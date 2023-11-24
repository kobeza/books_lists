package com.kobeza_sv.bookslists.data.datasource.remote.retrofit

import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.BookDetailResponse
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.BookResponse
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksListsApi {
    @GET(BOOKS)
    suspend fun getBooks(): List<BookResponse>

    @GET(CATEGORIES)
    suspend fun getCategories(): List<CategoryResponse>

    @GET(BOOK_DETAIL)
    suspend fun getBookDetail(
        @Path("bookId") bookId: Long,
    ): BookDetailResponse

    companion object {
        private const val BOOKS = "KeskoSenukaiDigital/assignment/books"
        private const val CATEGORIES = "KeskoSenukaiDigital/assignment/lists"
        private const val BOOK_DETAIL = "KeskoSenukaiDigital/assignment/book/{bookId}"
    }
}