package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Selection(
    @SerializedName("perk")
    val perk: Int?,
    @SerializedName("var1")
    val var1: Int?,
    @SerializedName("var2")
    val var2: Int?,
    @SerializedName("var3")
    val var3: Int?
)