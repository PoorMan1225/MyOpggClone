package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName


data class Match(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("metadata")
    val metadata: Metadata?
)