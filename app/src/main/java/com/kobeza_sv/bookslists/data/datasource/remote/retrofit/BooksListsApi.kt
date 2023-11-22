package com.kobeza_sv.bookslists.data.datasource.remote.retrofit

import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.BookResponse
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksListsApi {
    @GET(BOOKS)
    suspend fun getBooks(): Response<List<BookResponse>>

    @GET(CATEGORIES)
    suspend fun getCategories(): Response<List<CategoryResponse>>

    @GET(BOOK_DETAIL)
    suspend fun getBookDetail(
        @Path("bookId") bookId: Long,
    ): Response<List<CategoryResponse>>

    companion object {
        private const val BOOKS = "/books"
        private const val CATEGORIES = "/lists"
        private const val BOOK_DETAIL = "/book/{bookId}"
    }
}