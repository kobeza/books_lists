package com.kobeza_sv.bookslists.domain

sealed class Result<out R>(open val data: R?) {

    data class Success<out T>(
        override val data: T,
    ) : Result<T>(data)

    data class Error<out T>(
        val exception: Throwable,
        override val data: T? = null,
    ) : Result<T>(data)

    data class Loading<T>(
        override val data: T? = null
    ) : Result<T>(data)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data"
            is Error -> "Error[exception=$exception,data=$data]"
            is Loading<*> -> "Loading[data=$data]"
        }
    }
}

fun <T> Result<T>.onSuccess(
    callback: (data: T) -> Unit
) {
    if (this is Result.Success) {
        callback(data)
    }
}

fun <T> Result<T>.onError(
    callback: (error: Throwable, data: T?) -> Unit
) {
    if (this is Result.Error) {
        callback(exception, data)
    }
}

fun <T> Result<T>.onLoading(
    callback: (data: T?) -> Unit
) {
    if (this is Result.Loading) {
        callback(data)
    }
}

inline fun <V : Any?, U : Any?> Result<V>.mapSuccess(transform: (V) -> U): Result<U> =
    try {
        when (this) {
            is Result.Success -> Result.Success(transform(data))
            is Result.Error -> Result.Error(exception)
            is Result.Loading -> Result.Loading()
        }
    } catch (ex: Throwable) {
        Result.Error(ex)
    }

inline fun <V : Any?, U : Any?> Result<V>.map(transform: (V?) -> U): Result<U> =
    try {
        when (this) {
            is Result.Success -> Result.Success(transform(data))
            is Result.Error -> Result.Error(
                exception,
                transform(data)
            )

            is Result.Loading -> Result.Loading(transform(data))
        }
    } catch (ex: Throwable) {
        Result.Error(ex)
    }