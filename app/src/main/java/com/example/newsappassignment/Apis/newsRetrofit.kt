package com.example.newsappassignment.Apis

import com.example.newsappassignment.models.NewsSource
import retrofit2.Call
import retrofit2.http.GET

const val api_key="75b1432acbe34671968e48e8dd272f02"
interface newsRetrofit {

    @GET(value= "sources?apiKey=${api_key}")
    fun getHeadlines(): Call<NewsSource>

    @GET(value= "sources?category=business&apiKey=${api_key}")
    fun getBusinessHeadlines(): Call<NewsSource>

    @GET("sources?category=entertainment&apiKey=${api_key}")
    fun getEntertainmentHeadlines(): Call<NewsSource>

    @GET("sources?category=general&apiKey=${api_key}")
    fun getGeneralHeadlines(): Call<NewsSource>

    @GET("sources?category=health&apiKey=${api_key}")
    fun getHealthHeadlines(): Call<NewsSource>

    @GET("sources?category=science&apiKey=${api_key}")
    fun getScienceHeadlines(): Call<NewsSource>

    @GET("sources?category=sports&apiKey=${api_key}")
    fun getSportsHeadlines(): Call<NewsSource>

    @GET("sources?category=technology&apiKey=${api_key}")
    fun getTechnologyHeadlines(): Call<NewsSource>
}