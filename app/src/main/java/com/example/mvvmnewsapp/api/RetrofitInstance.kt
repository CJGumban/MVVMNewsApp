package com.example.mvvmnewsapp.api

import com.example.mvvmnewsapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    /*enables to make request from anywhere in the code
    * retrofit by lazy means that it will be initialized once*/
    /*
    *httplogginginterceptors allows to log responses from retrofit which is useful for debugging*/
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        /*api instance from retrofit builder
        *this is the actual api object that will be used from anywhere to make actual network request*/
        val api by lazy{
            retrofit.create(NewsAPI::class.java)
        }
    }
}