package com.example.newsappassignment.models

import com.google.gson.annotations.SerializedName

data class NewsSource(
    @SerializedName("status")
    val status: String,

    @SerializedName("sources")
    val sources: List<newsSourceModel>
)

