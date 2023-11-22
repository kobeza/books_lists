package com.kobeza_sv.bookslists.data.repository

import com.kobeza_sv.bookslists.data.datasource.local.db.BooksListsDao
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.BooksListsApi
import com.kobeza_sv.bookslists.domain.BooksListsRepository
import javax.inject.Inject

class BooksListsRepositoryImpl @Inject constructor(
    val dao: BooksListsDao,
    val api: BooksListsApi,
) : BooksListsRepository {
}