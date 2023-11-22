package com.kobeza_sv.bookslists.data.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
)