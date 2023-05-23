package com.example.ksptest.data

import com.google.gson.annotations.SerializedName

data class UnsplashUser(
    @field:SerializedName("name") val name:String,
    @field:SerializedName("username") val username:String
){
    val attributionUrl:String
        get() {
            return "https://unsplash.com/$username?utm_source=sunflower&utm_medium=referral"
        }
}
