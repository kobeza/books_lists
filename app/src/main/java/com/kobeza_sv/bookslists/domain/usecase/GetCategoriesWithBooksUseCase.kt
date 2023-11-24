package com.kobeza_sv.bookslists.domain.usecase

import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.CategoryWithBooks
import com.kobeza_sv.bookslists.domain.repository.BooksListsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesWithBooksUseCase @Inject constructor(
    private val repository: BooksListsRepository
) {
    suspend operator fun invoke(refresh: Boolean): Flow<Result<List<CategoryWithBooks>>> {
        return repository.getCategoryWithBooks(refresh)
    }
}