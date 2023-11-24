package com.kobeza_sv.bookslists.presentation.allcategories

import androidx.lifecycle.viewModelScope
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.mapSuccess
import com.kobeza_sv.bookslists.domain.usecase.GetCategoriesWithBooksUseCase
import com.kobeza_sv.bookslists.presentation.base.BaseViewModel
import com.kobeza_sv.bookslists.presentation.recycler.BooksListsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCategoriesViewModel @Inject constructor(
    private val getCategoriesWithBooks: GetCategoriesWithBooksUseCase,
) : BaseViewModel() {

    private val _categoriesWithBooks =
        MutableStateFlow<Result<List<BooksListsAdapter.Data.AllCategories>>>(Result.Loading())
    val categoriesWithBooks: StateFlow<Result<List<BooksListsAdapter.Data.AllCategories>>>
        get() = _categoriesWithBooks

    init {
        loadData(false)
    }

    fun onShowAllButtonClicked(categoryId: Long) {
        navigateTo(
            AllCategoriesFragmentDirections.actionAllCategoriesToSelectedCategory(categoryId)
        )
    }

    fun onBookClicked(bookId: Long) {
        navigateTo(
            AllCategoriesFragmentDirections.actionAllCategoriesToBookDetails(bookId)
        )
    }

    fun onRefresh() {
        loadData(true)
    }

    private fun loadData(refresh: Boolean) {
        viewModelScope.launch {
            getCategoriesWithBooks(refresh).catch {
                emit(Result.Error(it))
            }.collect {
                it.mapSuccess {
                    it.map {
                        BooksListsAdapter.Data.AllCategories(it)
                    }
                }.let {
                    _categoriesWithBooks.value = it
                }
            }
        }
    }
}