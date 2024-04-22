package com.example.newsappassignment.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataServices {

    private val url ="https://newsapi.org/v2/top-headlines/"

    private val retrofit= Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}