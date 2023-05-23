package com.example.ksptest.data.response

import com.example.ksptest.data.UnsplashPhoto
import com.google.gson.annotations.SerializedName

data class UnsplashSearchResponse(
    @field:SerializedName("results") val results:List<UnsplashPhoto>,
    @field:SerializedName("total_page") val totalPages:Int
)
