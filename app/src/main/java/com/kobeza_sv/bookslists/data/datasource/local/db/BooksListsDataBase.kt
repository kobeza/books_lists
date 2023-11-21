package com.kobeza_sv.bookslists.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.Book
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetail
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.Category

@Database(
    entities = [Book::class, BookDetail::class, Category::class],
    version = 1
)
abstract class BooksListsDataBase : RoomDatabase() {
    abstract val booksListsDao: BooksListsDao
}