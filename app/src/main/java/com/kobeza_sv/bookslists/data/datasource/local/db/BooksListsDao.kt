package com.kobeza_sv.bookslists.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Upsert
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.Book
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetail
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.Category

@Dao
interface BooksListsDao {
    @Upsert
    suspend fun upsertBook(book: Book)

    @Upsert
    suspend fun upsertBookDetail(bookDetail: BookDetail)

    @Upsert
    suspend fun upsertCategory(category: Category)
}