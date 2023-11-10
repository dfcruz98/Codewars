package com.example.codewars.data.remote.utils

import retrofit2.Response

sealed class RequestResult<T> {
    data class Success<T>(val value: T) : RequestResult<T>()
    data class Error<T>(val message: String? = null) : RequestResult<T>()
    data class Exception<T>(val ex: java.lang.Exception) : RequestResult<T>()
}

inline fun <V, T> T.tryMakingRequest(
    request: T.() -> Response<V>,
): RequestResult<V> {
    val response: Response<V>

    try {
        response = request()
    } catch (ex: Exception) {
        return RequestResult.Exception(ex)
    }

    return if (response.isSuccessful) {
        val body = response.body()
        if (body == null) {
            RequestResult.Error()
        } else {
            RequestResult.Success(body)
        }
    } else {
        RequestResult.Error()
    }
}
