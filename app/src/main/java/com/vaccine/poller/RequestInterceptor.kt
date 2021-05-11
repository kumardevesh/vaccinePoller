package com.vaccine.poller

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */

    companion object{
        var myAuthToken =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJjODM2MjE0NC00MThiLTQ4MWUtYjI4Mi0xMWY5MzU4YmU3ZTEiLCJ1c2VyX2lkIjoiYzgzNjIxNDQtNDE4Yi00ODFlLWIyODItMTFmOTM1OGJlN2UxIiwidXNlcl90eXBlIjoiQkVORUZJQ0lBUlkiLCJtb2JpbGVfbnVtYmVyIjo4ODAyNDYzNDA0LCJiZW5lZmljaWFyeV9yZWZlcmVuY2VfaWQiOjU3OTI1MTg0ODYzMDcsInNlY3JldF9rZXkiOiJiNWNhYjE2Ny03OTc3LTRkZjEtODAyNy1hNjNhYTE0NGYwNGUiLCJ1YSI6Ik1vemlsbGEvNS4wIChNYWNpbnRvc2g7IEludGVsIE1hYyBPUyBYIDEwXzE1XzYpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIENocm9tZS85MC4wLjQ0MzAuOTMgU2FmYXJpLzUzNy4zNiIsImRhdGVfbW9kaWZpZWQiOiIyMDIxLTA1LTA4VDA3OjI5OjQxLjQ4NVoiLCJpYXQiOjE2MjA0NTg5ODEsImV4cCI6MTYyMDQ1OTg4MX0.OlnRxY-QRZZHJjQNIj7tsamhmCXUffvEUvpq2L-yUjE"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val userAgent = "Android ${Build.VERSION.RELEASE}; ${Build.MODEL} ${Build.MANUFACTURER}; "

        val requestBuilder = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", userAgent)

        if (getAccessToken().isEmpty()) {
            return chain.proceed(chain.request())
        } else {
            requestBuilder.addHeader("authorization", getAccessToken()).build()
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun getAccessToken(): String {
        return myAuthToken
    }
}