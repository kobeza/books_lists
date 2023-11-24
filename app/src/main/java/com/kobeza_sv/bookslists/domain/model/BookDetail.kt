package com.kobeza_sv.bookslists.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookDetail(
    val id: Long,
    val categoryId: Long,
    val isbn: String,
    val publicationDate: String,
    val author: String,
    val title: String,
    val img: String,
    val description: String,
):Parcelable