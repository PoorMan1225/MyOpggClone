package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Tower(
    @SerializedName("first")
    val first: Boolean?,
    @SerializedName("kills")
    val kills: Int?
)