package com.rjhwork.mycompany.opggcloneapp.data.entity.spell

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("SummonerBarrier")
    val summonerBarrier: SummonerBarrier,
    @SerializedName("SummonerBoost")
    val summonerBoost:SummonerBoost,
    @SerializedName("SummonerDot")
    val summonerDot:SummonerDot,
    @SerializedName("SummonerExhaust")
    val summonerExhaust:SummonerExhaust,
    @SerializedName("SummonerFlash")
    val summonerFlash:SummonerFlash,
    @SerializedName("SummonerHaste")
    val summonerHaste:SummonerHaste,
    @SerializedName("SummonerHeal")
    val summonerHeal:SummonerHeal,
    @SerializedName("SummonerMana")
    val summonerMana:SummonerMana,
    @SerializedName("SummonerPoroRecall")
    val summonerPoroRecall:SummonerPoroRecall,
    @SerializedName("SummonerPoroThrow")
    val summonerPoroThrow:SummonerPoroThrow,
    @SerializedName("SummonerSmite")
    val summonerSmite:SummonerSmite,
    @SerializedName("SummonerSnowball")
    val summonerSnowball:SummonerSnowball,
    @SerializedName("SummonerTeleport")
    val summonerTeleport:SummonerTeleport,
    @SerializedName("Summoner_UltBookPlaceholder")
    val summoner_UltBook_Placeholder:Summoner_UltBookPlaceholder,
    @SerializedName("Summoner_UltBookSmitePlaceholder")
    val summoner_UltBookSmitePlaceHolder:Summoner_UltBookSmitePlaceholder
) {
}