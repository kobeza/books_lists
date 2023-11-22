package com.kobeza_sv.bookslists.data.repository

import com.kobeza_sv.bookslists.data.datasource.local.db.BooksListsDao
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.BooksListsApi
import com.kobeza_sv.bookslists.data.mapper.toEntity
import com.kobeza_sv.bookslists.data.mapper.toModel
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.CategoryWithBooks
import com.kobeza_sv.bookslists.domain.repository.BooksListsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BooksListsRepositoryImpl @Inject constructor(
    private val dao: BooksListsDao,
    private val api: BooksListsApi,
) : BooksListsRepository {

    override suspend fun getCategoryWithBooks(refresh: Boolean): Flow<Result<List<CategoryWithBooks>>> {
        return flow {
            emit(Result.Loading())
            if (refresh) {
                loadCategoryWithBookFromRemoteAndStoreResult()
            } else {
                getCategoryWithBooksFromLocal()
            }.let {
                emit(Result.Success(it))
            }
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
}