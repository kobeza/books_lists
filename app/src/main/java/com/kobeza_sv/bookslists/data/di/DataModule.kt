package com.kobeza_sv.bookslists.data.di

import android.content.Context
import androidx.room.Room
import com.kobeza_sv.bookslists.data.datasource.local.db.BooksListsDataBase
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.BooksListsApi
import com.kobeza_sv.bookslists.data.repository.BooksListsRepositoryImpl
import com.kobeza_sv.bookslists.domain.BooksListsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Provides
    fun provideBooksListsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        BooksListsDataBase::class.java,
        "books_lists_db"
    ).build()

    @Singleton
    @Provides
    fun provideBooksListsDao(db: BooksListsDataBase) = db.booksListsDao

    @Provides
    @Singleton
    fun provideRetrofit(retrofit: Retrofit): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/KeskoSenukaiDigital/assignment/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideBooksListsApi(retrofit: Retrofit): BooksListsApi =
        retrofit.create(BooksListsApi::class.java)

    @Binds
    @Singleton
    abstract fun bindBooksListsRepository(repository: BooksListsRepositoryImpl): BooksListsRepository
}