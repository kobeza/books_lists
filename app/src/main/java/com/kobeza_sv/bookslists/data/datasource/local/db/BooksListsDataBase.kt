package com.kobeza_sv.bookslists.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetailEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.CategoryEntity

@Database(
    entities = [BookEntity::class, BookDetailEntity::class, CategoryEntity::class],
    version = 1
)
abstract class BooksListsDataBase : RoomDatabase() {
    abstract val booksListsDao: BooksListsDao
}