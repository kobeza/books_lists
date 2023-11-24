package com.kobeza_sv.bookslists.domain.usecase

import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.repository.BooksListsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksDetailsByCategoryUseCase @Inject constructor(
    private val repository: BooksListsRepository
) {
    suspend operator fun invoke(
        refresh: Boolean,
        categoryId: Long
    ): Flow<Result<List<BookDetail>>> {
        return repository.getBooksDetailByCategory(
            refresh = refresh,
            categoryId = categoryId,
        )
    }
}