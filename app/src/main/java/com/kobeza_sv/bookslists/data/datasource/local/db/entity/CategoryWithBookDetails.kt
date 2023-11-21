package com.kobeza_sv.bookslists.data.datasource.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithBookDetails(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val booksDetail:List<BookDetail>
)
