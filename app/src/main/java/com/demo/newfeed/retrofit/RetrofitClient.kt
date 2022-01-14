package com.demo.newfeed.retrofit

import com.demo.newfeed.BaseConfig
import com.demo.newfeed.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        fun getClient(): Retrofit {

            val authorization = "Bearer " + BaseConfig.API_KEY()

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", authorization)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }

            var client = httpClient
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                client = httpClient
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
            }

            return Retrofit.Builder()
                .baseUrl(BaseConfig.BASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}