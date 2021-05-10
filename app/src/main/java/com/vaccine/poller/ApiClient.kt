package com.vaccine.poller

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        private var retrofit: Retrofit? = null

        fun getClient(context: Context): Retrofit? {

            val client = OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .addInterceptor(ChuckInterceptor(context))
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl("https://cdn-api.co-vin.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }
    }
}