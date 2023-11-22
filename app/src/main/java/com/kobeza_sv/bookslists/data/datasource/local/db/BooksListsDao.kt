package com.kobeza_sv.bookslists.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetailEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.CategoryEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.CategoryWithBooksEntity

@Dao
interface BooksListsDao {
    @Upsert
    suspend fun upsertBooks(books: List<BookEntity>)

    @Upsert
    suspend fun upsertBookDetail(bookDetail: BookDetailEntity)

    @Upsert
    suspend fun upsertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategoryWithBooks(): List<CategoryWithBooksEntity>
}