package com.kobeza_sv.bookslists.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
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
    suspend fun upsertBooksDetail(booksDetails: List<BookDetailEntity>)

    @Upsert
    suspend fun upsertBookDetail(bookDetail: BookDetailEntity)

    @Upsert
    suspend fun upsertCategories(categories: List<CategoryEntity>)

    @Transaction
    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategoryWithBooks(): List<CategoryWithBooksEntity>

    @Query("SELECT * FROM BookDetailEntity WHERE categoryId =:categoryId")
    suspend fun getBooksDetailByCategory(categoryId: Long): List<BookDetailEntity>

    @Query("SELECT id FROM BookEntity WHERE categoryId =:categoryId")
    suspend fun getBookIdsByCategory(categoryId: Long): List<Long>

    @Query("SELECT * FROM BookDetailEntity WHERE id =:id")
    suspend fun getBookDetailById(id: Long): BookDetailEntity?
}