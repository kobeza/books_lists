package com.kobeza_sv.bookslists.domain.model

data class CategoryWithBooks(
    val category: Category,
    val books: List<Book>,
)