package com.kobeza_sv.bookslists.domain.usecase

import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.repository.BooksListsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(
    private val repository: BooksListsRepository
) {
    suspend operator fun invoke(
        refresh: Boolean,
        bookId: Long,
    ): Flow<Result<BookDetail>> {
        return repository.getBooksDetailById(
            refresh = refresh,
            bookId = bookId,
        )
    }
}