package com.example.data.utilis

import com.example.data.utilis.errors.ConflictError
import com.example.data.utilis.errors.EmptyError
import com.example.data.utilis.errors.UnAuthorizedError
import com.example.data.utilis.errors.UnKnownError
import retrofit2.Response


fun <T> Response<T>.getResponse(): T {
    if (checkResponse()) {
        val body = body()
        if (body != null) {
            return body()!!
        } else {
            throw EmptyError(message = "Empty Body")
        }
    } else if (code() == 401) {
        throw UnAuthorizedError(message())
    } else if (code() == 409) {
        throw ConflictError(
            code = code(),
            message = "You have exceeded the maximum number of archives. You must delete one of the archives to be able to add another"
        )
    } else {
        throw UnKnownError(code = code(), message = message())
    }
}

fun <T> Response<T>.checkResponse(): Boolean {
    if (isSuccessful) {
        return true
    } else if (code() == 401) {
        throw UnAuthorizedError(message())
    } else if (code() == 409) {
        throw ConflictError(
            code = code(),
            message = "You have exceeded the maximum number of archives. You must delete one of the archives to be able to add another"
        )
    } else {
        throw UnKnownError(code = code(), message = message())
    }
}