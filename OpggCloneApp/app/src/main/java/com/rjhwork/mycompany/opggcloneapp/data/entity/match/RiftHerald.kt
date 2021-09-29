package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class RiftHerald(
    @SerializedName("first")
    val first: Boolean?,
    @SerializedName("kills")
    val kills: Int?
)