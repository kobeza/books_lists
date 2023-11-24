package com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class BookDetailResponse(
    val id: Long?,
    @SerializedName("list_id")
    val categoryId: Long?,
    val isbn: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    val author: String?,
    val title: String?,
    val img: String?,
    val description: String?,
)
