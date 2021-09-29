package com.rjhwork.mycompany.opggcloneapp.data.entity.spell

import com.google.gson.annotations.SerializedName

data class Spell(
    @SerializedName("type")
    val type: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("data")
    val data:Data
)