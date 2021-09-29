package com.rjhwork.mycompany.opggcloneapp.data.entity.spell

import com.google.gson.annotations.SerializedName

data class SummonerHaste(
    @SerializedName("id")
    val id:String,
    @SerializedName("key")
    val key:String
) {
}