package com.rjhwork.mycompany.opggcloneapp.data.entity.rune


import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("slots")
    val slots: List<Slot>?
)