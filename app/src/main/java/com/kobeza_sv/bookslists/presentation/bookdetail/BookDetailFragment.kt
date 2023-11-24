package com.kobeza_sv.bookslists.presentation.bookdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kobeza_sv.bookslists.R
import com.kobeza_sv.bookslists.databinding.FragmentBookDetailsBinding
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.onError
import com.kobeza_sv.bookslists.domain.onLoading
import com.kobeza_sv.bookslists.domain.onSuccess
import com.kobeza_sv.bookslists.presentation.base.BaseFragment
import com.kobeza_sv.bookslists.presentation.provideViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailFragment : BaseFragment<FragmentBookDetailsBinding>() {

    private val args: BookDetailFragmentArgs by navArgs()

    @Inject
    lateinit var factory: BookDetailsViewModel.Factory
    private val viewModel by viewModels<BookDetailsViewModel> {
        provideViewModel {
            factory.create(
                bookId = args.bookId
            )
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBookDetailsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookDetail.collect {
                handleBookDetailResult(it)
            }
        }
    }

    private fun handleBookDetailResult(result: Result<BookDetail>) {
        with(result) {
            onLoading {
                updateUiState(UiState.Loading)
            }
            onSuccess {
                updateUiState(UiState.Success)
                updateViews(it)
            }
            onError { _, _ ->
                updateUiState(UiState.Error)
            }
        }
    }

    private fun updateViews(bookDetail: BookDetail) {
        with(binding) {
            bookTitle.text = bookDetail.title
            author.text = bookDetail.author
            isbn.text = bookDetail.isbn
            publicationDate.text = bookDetail.publicationDate
            description.text = bookDetail.description
            Glide
                .with(requireContext())
                .load(bookDetail.img)
                .centerCrop()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(image)
        }
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }
}