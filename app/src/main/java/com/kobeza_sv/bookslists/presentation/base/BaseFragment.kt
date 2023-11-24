package com.kobeza_sv.bookslists.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.kobeza_sv.bookslists.R
import com.kobeza_sv.bookslists.presentation.makeGone

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    open fun onRefresh() {}

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!
    private val progressContainer: View?
        get() = binding.root.findViewById(R.id.progress)
    private val errorContainer: View?
        get() = binding.root.findViewById(R.id.error)
    private val retryButton: View?
        get() = binding.root.findViewById(R.id.button_retry)
    private val swipeRefresh: SwipeRefreshLayout?
        get() = binding.root.findViewById(R.id.swipe_refresh)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        retryButton?.setOnClickListener {
            errorContainer?.makeGone()
            onRefresh()
        }
        swipeRefresh?.setOnRefreshListener {
            onRefresh()
        }
    }

    fun updateUiState(state: UiState) {
        with(state) {
            progressContainer?.visibility = progressVisibility
            swipeRefresh?.isRefreshing = refreshIndicatorVisibility
            errorContainer?.visibility = errorVisibility
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    sealed class UiState(
        val progressVisibility: Int,
        val refreshIndicatorVisibility: Boolean,
        val errorVisibility: Int,
    ) {
        data object Loading : UiState(
            progressVisibility = View.VISIBLE,
            refreshIndicatorVisibility = false,
            errorVisibility = View.GONE,
        )

        data object Success : UiState(
            progressVisibility = View.GONE,
            refreshIndicatorVisibility = false,
            errorVisibility = View.GONE,
        )

        data object Error : UiState(
            progressVisibility = View.GONE,
            refreshIndicatorVisibility = false,
            errorVisibility = View.VISIBLE,
        )
    }
}