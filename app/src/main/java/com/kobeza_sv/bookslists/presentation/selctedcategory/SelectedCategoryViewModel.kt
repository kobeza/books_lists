package com.kobeza_sv.bookslists.presentation.selctedcategory

import androidx.lifecycle.viewModelScope
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.mapSuccess
import com.kobeza_sv.bookslists.domain.usecase.GetBooksDetailsByCategoryUseCase
import com.kobeza_sv.bookslists.presentation.base.BaseViewModel
import com.kobeza_sv.bookslists.presentation.recycler.BooksListsAdapter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SelectedCategoryViewModel @AssistedInject constructor(
    @Assisted private val categoryId: Long,
    private val getBooksDetailsByCategoryUseCase: GetBooksDetailsByCategoryUseCase,
) : BaseViewModel() {

    private val _bookDetails =
        MutableStateFlow<Result<List<BooksListsAdapter.Data.SelectedCategory>>>(
            Result.Loading()
        )
    val bookDetails: StateFlow<Result<List<BooksListsAdapter.Data.SelectedCategory>>>
        get() = _bookDetails

    init {
        loadData(false)
    }

    fun onBookClicked(bookId: Long) {
        navigateTo(
            SelectedCategoryFragmentDirections.actionSelectedCategoryToBookDetails(bookId)
        )
    }

    fun onRefresh() {
        loadData(true)
    }

    private fun loadData(refresh: Boolean) {
        viewModelScope.launch {
            getBooksDetailsByCategoryUseCase(
                refresh = refresh,
                categoryId = categoryId
            ).catch {
                emit(Result.Error(it))
            }.collect {
                it.mapSuccess {
                    it.map {
                        BooksListsAdapter.Data.SelectedCategory(it)
                    }
                }.let {
                    _bookDetails.value = it
                }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(
            categoryId: Long,
        ): SelectedCategoryViewModel
    }
}