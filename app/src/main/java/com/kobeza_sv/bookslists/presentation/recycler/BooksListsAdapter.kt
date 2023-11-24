package com.kobeza_sv.bookslists.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kobeza_sv.bookslists.R
import com.kobeza_sv.bookslists.databinding.ItemAllCategoriesBinding
import com.kobeza_sv.bookslists.databinding.ItemSelectedCategoryBinding
import com.kobeza_sv.bookslists.databinding.ItemSingleCategoryBinding
import com.kobeza_sv.bookslists.domain.model.Book
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.model.CategoryWithBooks
import com.kobeza_sv.bookslists.presentation.addSpacingDecoration

class BooksListsAdapter(
    private val onShowAllClick: ((categoryId: Long) -> Unit)? = null,
    private val onItemClick: ((bookId: Long) -> Unit)? = null,
) : ListAdapter<BooksListsAdapter.Data, RecyclerView.ViewHolder>(DefaultItemDiffCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).itemViewType.ordinal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ItemViewType.ALL_CATEGORY.ordinal -> AllCategoriesViewHolder(
                ItemAllCategoriesBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            ItemViewType.SINGLE_CATEGORY.ordinal -> SingleCategoryViewHolder(
                ItemSingleCategoryBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            ItemViewType.SELECTED_CATEGORY.ordinal -> SelectedCategoryViewHolder(
                ItemSelectedCategoryBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is AllCategoriesViewHolder -> {
                holder.bind(
                    item = (item as Data.AllCategories).data,
                    onShowAllClick = onShowAllClick,
                    onItemClick = onItemClick,
                )
            }

            is SingleCategoryViewHolder -> {
                holder.bind(
                    item = (item as Data.SingleCategory).data,
                    onItemClick = onItemClick,
                )
            }

            is SelectedCategoryViewHolder -> {
                holder.bind(
                    item = (item as Data.SelectedCategory).data,
                    onItemClick = onItemClick,
                )
            }
        }
    }

    class AllCategoriesViewHolder(
        private val binding: ItemAllCategoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: CategoryWithBooks,
            onShowAllClick: ((categoryId: Long) -> Unit)?,
            onItemClick: ((bookId: Long) -> Unit)?
        ) {
            with(binding) {
                title.text = item.category.title
                setupChildRecycler(
                    books = item.books,
                    onItemClick = onItemClick
                )
                buttonShowAll.setOnClickListener {
                    onShowAllClick?.invoke(item.category.id)
                }
            }
        }

        private fun setupChildRecycler(books: List<Book>, onItemClick: ((bookId: Long) -> Unit)?) {
            val adapter = BooksListsAdapter(
                onItemClick = onItemClick
            )
            with(binding){
                recycler.adapter = adapter
                recycler.addSpacingDecoration(
                    spacing = R.dimen.spacing_8,
                    orientation = RecyclerView.HORIZONTAL
                )
            }

            books.map {
                Data.SingleCategory(it)
            }.let {
                adapter.submitList(it)
            }
        }
    }

    class SingleCategoryViewHolder(
        private val binding: ItemSingleCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Book,
            onItemClick: ((bookId: Long) -> Unit)?
        ) {
            with(binding) {
                title.text = item.title
                Glide
                    .with(itemView.context)
                    .load(item.img)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(image)
                root.setOnClickListener {
                    onItemClick?.invoke(item.id)
                }
            }
        }
    }

    class SelectedCategoryViewHolder(
        private val binding: ItemSelectedCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: BookDetail,
            onItemClick: ((bookId: Long) -> Unit)?
        ) {
            with(binding) {
                title.text = item.title
                author.text = item.author
                Glide
                    .with(itemView.context)
                    .load(item.img)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(image)
                root.setOnClickListener {
                    onItemClick?.invoke(item.id)
                }
            }
        }
    }

    enum class ItemViewType {
        ALL_CATEGORY,
        SINGLE_CATEGORY,
        SELECTED_CATEGORY,
    }

    sealed class Data(
        val itemViewType: ItemViewType
    ) {
        data class AllCategories(
            val data: CategoryWithBooks
        ) : Data(
            itemViewType = ItemViewType.ALL_CATEGORY
        )

        data class SingleCategory(
            val data: Book
        ) : Data(
            itemViewType = ItemViewType.SINGLE_CATEGORY
        )

        data class SelectedCategory(
            val data: BookDetail
        ) : Data(
            itemViewType = ItemViewType.SELECTED_CATEGORY
        )
    }
}