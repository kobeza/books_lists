package com.kobeza_sv.bookslists.presentation.bookdetail

import androidx.lifecycle.viewModelScope
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.mapSuccess
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.usecase.GetBookDetailUseCase
import com.kobeza_sv.bookslists.presentation.base.BaseViewModel
import com.kobeza_sv.bookslists.presentation.isoToSimpleFormat
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class BookDetailsViewModel @AssistedInject constructor(
    @Assisted private val bookId: Long,
    private val getBookDetailUseCase: GetBookDetailUseCase,
) : BaseViewModel() {

    private val _bookDetail =
        MutableStateFlow<Result<BookDetail>>(Result.Loading())
    val bookDetail: StateFlow<Result<BookDetail>>
        get() = _bookDetail

    init {
        loadData(false)
    }

    fun onRefresh() {
        loadData(true)
    }

    private fun loadData(refresh: Boolean) {
        viewModelScope.launch {
            getBookDetailUseCase(
                refresh = refresh,
                bookId = bookId
            ).map {
                it.mapSuccess {
                    it.copy(
                        publicationDate = it.publicationDate.isoToSimpleFormat()
                    )
                }
            }.catch {
                emit(Result.Error(it))
            }
                .collect {
                    _bookDetail.emit(it)
                }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(
            bookId: Long,
        ): BookDetailsViewModel
    }
}