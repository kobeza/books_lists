package com.kobeza_sv.bookslists.domain.model

data class Book(
    val id: Long,
    val categoryId: Long,
    val title: String,
    val img: String,
)