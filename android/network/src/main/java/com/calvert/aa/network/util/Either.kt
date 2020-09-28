package com.calvert.aa.network.util

sealed class Either<out Failure, out Success> {
    data class Failure<out Failure>(val failure: Failure): Either<Failure, Nothing>()
    data class Success<out Success>(val success: Success): Either<Nothing, Success>()

    companion object{
        fun<Failure> failure(value: Failure): Either<Failure, Nothing> = Failure(value)
        fun<Success> success(value: Success): Either<Nothing, Success> = Success(value)
    }
}

inline fun <Failure, Success> Either<Failure, Success>.onSuccess(callback: (Success) -> Unit): Either<Failure, Success> = apply {
    if (this is Either.Success) {
        callback(success)
    }
}

inline fun <Failure, Success> Either<Failure, Success>.onFailure(callback: (Failure) -> Unit): Either<Failure, Success> = apply {
    if (this is Either.Failure) {
        callback(failure)
    }
}