package com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response

data class BookDetailResponse(
    val id: Long?,
    val categoryId: Long?,
    val isbn: String?,
    val publicationDate: String?,
    val author: String?,
    val title: String?,
    val img: String?,
    val description: String?,
)
