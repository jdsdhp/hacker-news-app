package com.jesusd0897.hackernews.data.network.util

import android.content.Context
import com.jesusd0897.hackernews.R
import retrofit2.Response

private val NETWORK_CODES = mapOf(
    //Successful responses.
    200 to R.string.http_res_200,
    201 to R.string.http_res_201,
    //Client errors.
    400 to R.string.http_res_400,
    401 to R.string.http_res_401,
    402 to R.string.http_res_402,
    403 to R.string.http_res_403,
    404 to R.string.http_res_404,
    405 to R.string.http_res_405,
    406 to R.string.http_res_406,
    408 to R.string.http_res_408,
    410 to R.string.http_res_410,
    413 to R.string.http_res_413,
    414 to R.string.http_res_414,
    415 to R.string.http_res_415,
    423 to R.string.http_res_423,
    429 to R.string.http_res_429,
    //Server errors.
    500 to R.string.http_res_500,
    501 to R.string.http_res_501,
    502 to R.string.http_res_502,
    503 to R.string.http_res_503,
    504 to R.string.http_res_504,
    505 to R.string.http_res_505,
).withDefault { R.string.http_error_default }

internal sealed class ResponseResult<out T : Any?> {
    data class Success<out T : Any?>(val data: T? = null) : ResponseResult<T>()
    data class Loading(val message: String? = null) : ResponseResult<Nothing>()
    data class Error(val exception: Pair<Int, String>) : ResponseResult<Nothing>()
}

internal interface RequestHandler {

    suspend fun <T : Any?> safeApiCall(call: suspend () -> Response<T>): ResponseResult<T>

}

internal class RequestHandlerImpl(private val context: Context) : RequestHandler {

    override suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ResponseResult<T> =
        try {
            ResponseResult.Loading()
            val response = call.invoke()
            if (response.isSuccessful) ResponseResult.Success(response.body())
            else ResponseResult.Error(
                exception = Pair(
                    first = response.code(),
                    second = context.getString(
                        NETWORK_CODES.getOrElse(
                            key = response.code(),
                            defaultValue = { R.string.http_error_default }),
                    ),
                )
            )
        } catch (e: Exception) {
            ResponseResult.Error(
                exception = Pair(
                    first = -1,
                    second = context.getString(
                        NETWORK_CODES.getOrElse(
                            key = -1,
                            defaultValue = { R.string.http_error_default }),
                    ),
                )
            )
        }

}



