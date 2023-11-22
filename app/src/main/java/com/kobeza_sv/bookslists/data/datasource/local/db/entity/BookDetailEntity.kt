package com.kobeza_sv.bookslists.data.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookDetailEntity(
    @PrimaryKey
    val id: Long,
    val categoryId: Long,
    val isbn: String,
    val publicationDate: String,
    val author: String,
    val title: String,
    val img: String,
    val description: String,
)