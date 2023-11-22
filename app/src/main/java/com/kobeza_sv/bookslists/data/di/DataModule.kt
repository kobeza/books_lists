package com.kobeza_sv.bookslists.data.di

import android.content.Context
import androidx.room.Room
import com.kobeza_sv.bookslists.data.datasource.local.db.BooksListsDataBase
import com.kobeza_sv.bookslists.data.datasource.remote.retrofit.BooksListsApi
import com.kobeza_sv.bookslists.data.repository.BooksListsRepositoryImpl
import com.kobeza_sv.bookslists.domain.repository.BooksListsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

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
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    internal fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val requestTimeout: Long = 15
        val builder = OkHttpClient.Builder()
            .connectTimeout(requestTimeout, TimeUnit.SECONDS)
            .readTimeout(requestTimeout, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideBooksListsApi(retrofit: Retrofit): BooksListsApi =
        retrofit.create(BooksListsApi::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        abstract fun bindBooksListsRepository(repository: BooksListsRepositoryImpl): BooksListsRepository
    }
}