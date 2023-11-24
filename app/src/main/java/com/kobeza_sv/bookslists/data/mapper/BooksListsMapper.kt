package com.kobeza_sv.bookslists.data.mapper

import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookDetailEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.BookEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.CategoryEntity
import com.kobeza_sv.bookslists.data.datasource.local.db.entity.CategoryWithBooksEntity
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.BookDetailResponse
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.BookResponse
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.CategoryResponse
import com.kobeza_sv.bookslists.domain.model.Book
import com.kobeza_sv.bookslists.domain.model.BookDetail
import com.kobeza_sv.bookslists.domain.model.Category
import com.kobeza_sv.bookslists.domain.model.CategoryWithBooks

fun BookEntity.toModel() = Book(
    id = id,
    categoryId = categoryId,
    title = title,
    img = img,
)

fun BookDetailEntity.toModel() = BookDetail(
    id = id,
    categoryId = categoryId,
    isbn = isbn,
    publicationDate = publicationDate,
    author = author,
    title = title,
    img = img,
    description = description,
)

fun CategoryEntity.toModel() = Category(
    id = id,
    title = title,
)

fun CategoryWithBooksEntity.toModel() = CategoryWithBooks(
    category = category.toModel(),
    books = books.map { it.toModel() }
)

@JvmName("CategoryWithBooksEntitiesToModel")
fun List<CategoryWithBooksEntity>.toModel() = map {
    it.toModel()
}

fun List<CategoryResponse>.toEntity() = map {
    it.toEntity()
}

fun CategoryResponse.toEntity() = CategoryEntity(
    id = id.getOrDefault(),
    title = title.orEmpty(),
)

@JvmName("BookResponsesToEntity")
fun List<BookResponse>.toEntity() = map {
    it.toEntity()
}

fun BookResponse.toEntity() = BookEntity(
    id = id.getOrDefault(),
    categoryId = categoryId.getOrDefault(),
    title = title.orEmpty(),
    img = img.orEmpty(),
)

@JvmName("BookDetailResponsesToEntity")
fun List<BookDetailResponse>.toEntity() = map {
    it.toEntity()
}

fun BookDetailResponse.toEntity() = BookDetailEntity(
    id = id.getOrDefault(),
    categoryId = categoryId.getOrDefault(),
    isbn = isbn.orEmpty(),
    publicationDate = publicationDate.orEmpty(),
    author = author.orEmpty(),
    title = title.orEmpty(),
    img = img.orEmpty(),
    description = description.orEmpty(),
)

fun List<BookDetailResponse>.toModel() = map {
    it.toModel()
}

fun BookDetailResponse.toModel() = BookDetail(
    id = id.getOrDefault(),
    categoryId = categoryId.getOrDefault(),
    isbn = isbn.orEmpty(),
    publicationDate = publicationDate.orEmpty(),
    author = author.orEmpty(),
    title = title.orEmpty(),
    img = img.orEmpty(),
    description = description.orEmpty(),
)

@JvmName("BookDetailEntityToModel")
fun List<BookDetailEntity>.toModel() = map {
    it.toModel()
}

private fun Long?.getOrDefault() = this ?: LONG_DEFAULT_VALUE
private const val LONG_DEFAULT_VALUE = -1L