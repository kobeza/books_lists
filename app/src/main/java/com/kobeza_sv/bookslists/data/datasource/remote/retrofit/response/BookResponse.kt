package com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val id: Long?,
    @SerializedName("list_id")
    val categoryId: Long?,
    val title: String?,
    val img: String?,
)