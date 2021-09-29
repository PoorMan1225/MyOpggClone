package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Ban(
    @SerializedName("championId")
    val championId: Int? = null,
    @SerializedName("pickTurn")
    val pickTurn: Int? = null
)