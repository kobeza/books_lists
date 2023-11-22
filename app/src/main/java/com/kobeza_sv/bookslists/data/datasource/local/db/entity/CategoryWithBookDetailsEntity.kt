package com.kobeza_sv.bookslists.data.datasource.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithBookDetailsEntity(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val booksDetail:List<BookDetailEntity>
)