package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Perks(
    @SerializedName("statPerks")
    val statPerks: StatPerks?,
    @SerializedName("styles")
    val styles: List<Style>?
)