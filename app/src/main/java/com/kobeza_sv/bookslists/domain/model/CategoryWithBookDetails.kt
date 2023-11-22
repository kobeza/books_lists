package com.kobeza_sv.bookslists.domain.model

import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetailEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.CategoryEntity

data class CategoryWithBookDetails(
    val category: CategoryEntity,
    val booksDetail:List<BookDetailEntity>,
)