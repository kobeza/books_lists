package com.kobeza_sv.bookslists.data.datasource.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithBooksEntity(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val books:List<BookEntity>
)