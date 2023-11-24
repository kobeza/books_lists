package com.kobeza_sv.bookslists.presentation.selctedcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.kobeza_sv.bookslists.R
import com.kobeza_sv.bookslists.databinding.FragmentSelectedCategoryBinding
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.onError
import com.kobeza_sv.bookslists.domain.onLoading
import com.kobeza_sv.bookslists.domain.onSuccess
import com.kobeza_sv.bookslists.presentation.addSpacingDecoration
import com.kobeza_sv.bookslists.presentation.base.BaseFragment
import com.kobeza_sv.bookslists.presentation.provideViewModel
import com.kobeza_sv.bookslists.presentation.recycler.BooksListsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SelectedCategoryFragment : BaseFragment<FragmentSelectedCategoryBinding>() {

    private val args: SelectedCategoryFragmentArgs by navArgs()

    @Inject
    lateinit var factory: SelectedCategoryViewModel.Factory
    private val viewModel by viewModels<SelectedCategoryViewModel> {
        provideViewModel {
            factory.create(
                categoryId = args.categoryId
            )
        }
    }

    private val adapter by lazy {
        BooksListsAdapter(
            onItemClick = {
                viewModel.onBookClicked(it)
            }
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSelectedCategoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        with(binding) {
            recycler.adapter = adapter
            recycler.addSpacingDecoration(
                spacing = R.dimen.spacing_8,
                orientation = RecyclerView.VERTICAL
            )
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookDetails.collect {
                handleBookDetailsResult(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navDirections.collectLatest {
                findNavController().navigate(it)
            }
        }
    }

    private fun handleBookDetailsResult(result: Result<List<BooksListsAdapter.Data.SelectedCategory>>) {
        with(result) {
            onLoading {
                updateUiState(UiState.Loading)
            }
            onSuccess {
                updateUiState(UiState.Success)
                adapter.submitList(it)
            }
            onError { _, _ ->
                updateUiState(UiState.Error)
            }
        }
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }
}