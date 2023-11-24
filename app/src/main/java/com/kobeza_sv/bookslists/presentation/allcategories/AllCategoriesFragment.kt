package com.kobeza_sv.bookslists.presentation.allcategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kobeza_sv.bookslists.R
import com.kobeza_sv.bookslists.databinding.FragmentAllCategoriesBinding
import com.kobeza_sv.bookslists.domain.Result
import com.kobeza_sv.bookslists.domain.onError
import com.kobeza_sv.bookslists.domain.onLoading
import com.kobeza_sv.bookslists.domain.onSuccess
import com.kobeza_sv.bookslists.presentation.addSpacingDecoration
import com.kobeza_sv.bookslists.presentation.base.BaseFragment
import com.kobeza_sv.bookslists.presentation.recycler.BooksListsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllCategoriesFragment : BaseFragment<FragmentAllCategoriesBinding>() {
    private val viewModel: AllCategoriesViewModel by viewModels()
    private val adapter by lazy {
        BooksListsAdapter(
            onShowAllClick = {
                viewModel.onShowAllButtonClicked(it)
            },
            onItemClick = {
                viewModel.onBookClicked(it)
            }
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAllCategoriesBinding.inflate(inflater, container, false)

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
            viewModel.categoriesWithBooks.collect {
                handleCategoriesWihBooksResult(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navDirections.collectLatest {
                findNavController().navigate(it)
            }
        }
    }

    private fun handleCategoriesWihBooksResult(result: Result<List<BooksListsAdapter.Data.AllCategories>>) {
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