package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Objectives(
    @SerializedName("baron")
    val baron: Baron?,
    @SerializedName("champion")
    val champion: Champion?,
    @SerializedName("dragon")
    val dragon: Dragon?,
    @SerializedName("inhibitor")
    val inhibitor: Inhibitor?,
    @SerializedName("riftHerald")
    val riftHerald: RiftHerald?,
    @SerializedName("tower")
    val tower: Tower?
)