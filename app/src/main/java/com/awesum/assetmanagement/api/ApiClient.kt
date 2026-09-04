package com.awesum.assetmanagement.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    // ⚠️ BACKEND SETUP:
    // 1. Ensure XAMPP/WAMP is running MySQL and Apache.
    // 2. Import 'backend/schema.sql' into phpMyAdmin.
    // 3. Place the 'backend/' folder contents into your server's web root (e.g., C:/xampp/htdocs/awesum_api/).
    // 4. Update 'backend/db_config.php' with your database credentials.
    // 5. Update this BASE_URL below to match your server's IP or '10.0.2.2' for the Android Emulator.
    private const val BASE_URL = "http://192.168.114.60/awesum_api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
