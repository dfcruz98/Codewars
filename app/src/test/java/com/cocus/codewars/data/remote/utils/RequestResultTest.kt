package com.cocus.codewars.data.remote.utils

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class RequestResultTest {

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() = runTest {
        val lambdaResult = true
        val result = tryMakingRequest<Boolean, Any> { Response.success(lambdaResult) }
        assertThat(result).isInstanceOf(RequestResult.Success::class.java)
    }


    @Test
    fun `when lambda throws IOException then it should emit the result as NetworkError`() =
        runTest {
            val result = tryMakingRequest<Any, Any> { throw IOException() }
            assertThat(result).isInstanceOf(RequestResult.Exception::class.java)
        }


    @Test
    fun `when lambda throws HttpException then it should emit the result as Error`() {
        val errorBody =
            "{\"errors\": [\"Unexpected parameter\"]}".toResponseBody("application/json".toMediaTypeOrNull())

        runTest {
            val result = tryMakingRequest<Any, Any> {
                Response.error(422, errorBody)
            }

            assertThat(result).isInstanceOf(RequestResult.Error::class.java)
        }
    }

    @Test
    fun `when lambda throws unknown exception then it should emit ExceptionResult`() = runTest {
        val result = tryMakingRequest<Any, Any> { throw IllegalStateException() }
        assertThat(result).isInstanceOf(RequestResult.Exception::class.java)
    }
}
