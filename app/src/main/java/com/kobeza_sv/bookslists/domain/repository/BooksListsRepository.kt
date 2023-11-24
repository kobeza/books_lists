package com.kobeza_sv.bookslists.domain.repository

import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.model.CategoryWithBooks
import kotlinx.coroutines.flow.Flow

interface BooksListsRepository {
    suspend fun getCategoryWithBooks(refresh: Boolean = false): Flow<Result<List<CategoryWithBooks>>>
    suspend fun getBooksDetailByCategory(
        refresh: Boolean = false,
        categoryId: Long,
    ): Flow<Result<List<BookDetail>>>

    suspend fun getBooksDetailById(
        refresh: Boolean = false,
        bookId: Long,
    ): Flow<Result<BookDetail>>
}