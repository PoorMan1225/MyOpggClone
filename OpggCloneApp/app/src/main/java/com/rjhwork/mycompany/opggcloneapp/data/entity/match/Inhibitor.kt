package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Inhibitor(
    @SerializedName("first")
    val first: Boolean? = null,
    @SerializedName("kills")
    val kills: Int? = null
)