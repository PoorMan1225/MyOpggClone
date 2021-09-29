package com.rjhwork.mycompany.opggcloneapp.data.entity.spell

import com.google.gson.annotations.SerializedName

data class SummonerSmite(
    @SerializedName("id")
    val id:String,
    @SerializedName("key")
    val key:String
) {
}