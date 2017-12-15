package com.carlom.musixmatch.example.demo.presenter

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

@Component
class HttpClient @Autowired constructor() {

    val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor({ Logger.getLogger("OkHttp").log(Level.INFO, it) })
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


}
