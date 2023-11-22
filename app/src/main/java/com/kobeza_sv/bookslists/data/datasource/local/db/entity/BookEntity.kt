package com.kobeza_sv.bookslists.data.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey
    val id: Long,
    val categoryId: Long,
    val title: String,
    val img: String,
)