package com.rjhwork.mycompany.opggcloneapp.data.entity.rune


import com.google.gson.annotations.SerializedName

data class Slot(
    @SerializedName("runes")
    val runes: List<Rune>?
)