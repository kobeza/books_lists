package com.kobeza_sv.bookslists.data.repository

import com.kobeza_sv.bookslists.data.datasource.local.db.BooksListsDao
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetailEntity
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.BooksListsApi
import com.kobeza_sv.bookslists.data.mapper.toEntity
import com.kobeza_sv.bookslists.data.mapper.toModel
import com.kobeza_sv.bookslists.di.IoDispatcher
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.model.CategoryWithBooks
import com.kobeza_sv.bookslists.domain.repository.BooksListsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import javax.inject.Inject

class BooksListsRepositoryImpl @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val dao: BooksListsDao,
    private val api: BooksListsApi,
) : BooksListsRepository {

    override suspend fun getCategoryWithBooks(refresh: Boolean): Flow<Result<List<CategoryWithBooks>>> {
        return flow {
            emit(Result.Loading())
            val result = withContext(dispatcher) {
                if (refresh) {
                    loadCategoryWithBookFromRemoteAndStoreResult()
                } else {
                    getCategoryWithBooksFromLocal()
                }
            }
            emit(Result.Success(result))
        }
    }

    private suspend fun getCategoryWithBooksFromLocal(): List<CategoryWithBooks> {
        return dao.getCategoryWithBooks().toModel().takeIf { it.isNotEmpty() }
            ?: loadCategoryWithBookFromRemoteAndStoreResult()
    }

    private suspend fun loadCategoryWithBookFromRemoteAndStoreResult(): List<CategoryWithBooks> {
        return coroutineScope {
            val categories = async { api.getCategories() }
            val books = async { api.getBooks() }
            dao.upsertCategories(categories.await().toEntity())
            dao.upsertBooks(books.await().toEntity())
            dao.getCategoryWithBooks().toModel()
        }
    }

    override suspend fun getBooksDetailByCategory(
        refresh: Boolean,
        categoryId: Long
    ): Flow<Result<List<BookDetail>>> {
        return flow {
            emit(Result.Loading())
            val result = withContext(dispatcher) {
                val allBookIds = getBooksIdByCategory(categoryId)
                val existingBookDetails = dao.getBooksDetailByCategory(categoryId)

                if (refresh) {
                    getBooksDetailFromRemoteByCategoryAndStoreResult(
                        allBookIds = allBookIds,
                        existingBookDetails = emptyList(),
                    )
                } else {
                    getBooksDetailFromLocalByCategory(
                        allBookIds = allBookIds,
                        existingBookDetails = existingBookDetails,
                    )
                }
            }
            emit(Result.Success(result))
        }
    }

    private suspend fun getBooksDetailFromLocalByCategory(
        allBookIds: List<Long>,
        existingBookDetails: List<BookDetailEntity>
    ): List<BookDetail> {
        return if (allBookIds.size > existingBookDetails.size) {
            getBooksDetailFromRemoteByCategoryAndStoreResult(
                allBookIds = allBookIds,
                existingBookDetails = existingBookDetails,
            )
        } else existingBookDetails.toModel()
    }

    private suspend fun getBooksDetailFromRemoteByCategoryAndStoreResult(
        allBookIds: List<Long>,
        existingBookDetails: List<BookDetailEntity>
    ): List<BookDetail> {
        return coroutineScope {
            val existingBookDetailsIds = existingBookDetails.map { it.id }
            val booksDetailsResult = allBookIds
                .filter { bookId -> !existingBookDetailsIds.contains(bookId) }
                .map { async { api.getBookDetail(it) } }
                .awaitAll()
            dao.upsertBooksDetail(booksDetailsResult.toEntity())
            existingBookDetails.toModel() + booksDetailsResult.toModel()
        }
    }

    private suspend fun getBooksIdByCategory(categoryId: Long): List<Long> {
        return dao.getBookIdsByCategory(categoryId)
    }

    override suspend fun getBooksDetailById(
        refresh: Boolean,
        bookId: Long
    ): Flow<Result<BookDetail>> {
        return flow {
            emit(Result.Loading())
            val result = withContext(dispatcher) {
                if (refresh) {
                    getBooksDetailFromRemoteAndStoreResult(bookId)
                } else {
                    getBookDetailFromLocalById(bookId)
                }
            }
            emit(Result.Success(result))
        }
    }

    private suspend fun getBooksDetailFromRemoteAndStoreResult(bookId: Long): BookDetail {
        return api.getBookDetail(bookId)
            .also {
                dao.upsertBookDetail(it.toEntity())
            }.toModel()
    }

    private suspend fun getBookDetailFromLocalById(bookId: Long): BookDetail {
        return dao.getBookDetailById(bookId)?.toModel() ?: getBooksDetailFromRemoteAndStoreResult(
            bookId
        )
    }
}