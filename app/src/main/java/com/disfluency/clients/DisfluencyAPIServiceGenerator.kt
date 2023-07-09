package com.disfluency.clients

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object DisfluencyAPIServiceGenerator {

    private const val API_BASE_URL = "http://192.168.0.179:8081/"

    fun buildService(): DisfluencyAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return retrofit.create(DisfluencyAPIService::class.java);
    }
}
