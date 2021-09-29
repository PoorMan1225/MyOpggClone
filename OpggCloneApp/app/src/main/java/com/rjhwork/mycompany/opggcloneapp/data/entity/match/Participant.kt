package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Participant(
    @SerializedName("assists")
    val assists: Int? = null,
    @SerializedName("baronKills")
    val baronKills: Int? = null,
    @SerializedName("bountyLevel")
    val bountyLevel: Int? = null,
    @SerializedName("champExperience")
    val champExperience: Int? = null,
    @SerializedName("champLevel")
    val champLevel: Int? = null,
    @SerializedName("championId")
    val championId: Int?  = null,
    @SerializedName("championName")
    val championName: String? = null,
    @SerializedName("championTransform")
    val championTransform: Int?  = null,
    @SerializedName("consumablesPurchased")
    val consumablesPurchased: Int?  = null,
    @SerializedName("damageDealtToBuildings")
    val damageDealtToBuildings: Int?  = null,
    @SerializedName("damageDealtToObjectives")
    val damageDealtToObjectives: Int?  = null,
    @SerializedName("damageDealtToTurrets")
    val damageDealtToTurrets: Int?  = null,
    @SerializedName("damageSelfMitigated")
    val damageSelfMitigated: Int? = null,
    @SerializedName("deaths")
    val deaths: Int? = null,
    @SerializedName("detectorWardsPlaced")
    val detectorWardsPlaced: Int? = null,
    @SerializedName("doubleKills")
    val doubleKills: Int? = null ,
    @SerializedName("dragonKills")
    val dragonKills: Int? = null,
    @SerializedName("firstBloodAssist")
    val firstBloodAssist: Boolean? = null,
    @SerializedName("firstBloodKill")
    val firstBloodKill: Boolean? = null,
    @SerializedName("firstTowerAssist")
    val firstTowerAssist: Boolean? = null,
    @SerializedName("firstTowerKill")
    val firstTowerKill: Boolean? = null,
    @SerializedName("gameEndedInEarlySurrender")
    val gameEndedInEarlySurrender: Boolean? = null,
    @SerializedName("gameEndedInSurrender")
    val gameEndedInSurrender: Boolean? = null,
    @SerializedName("goldEarned")
    val goldEarned: Int? = null,
    @SerializedName("goldSpent")
    val goldSpent: Int? = null,
    @SerializedName("individualPosition")
    val individualPosition: String? = null,
    @SerializedName("inhibitorKills")
    val inhibitorKills: Int? = null,
    @SerializedName("inhibitorTakedowns")
    val inhibitorTakedowns: Int? = null,
    @SerializedName("inhibitorsLost")
    val inhibitorsLost: Int? = null,
    @SerializedName("item0")
    val item0: Int? = null,
    @SerializedName("item1")
    val item1: Int? = null,
    @SerializedName("item2")
    val item2: Int? = null,
    @SerializedName("item3")
    val item3: Int? = null,
    @SerializedName("item4")
    val item4: Int? = null,
    @SerializedName("item5")
    val item5: Int? = null,
    @SerializedName("item6")
    val item6: Int? = null,
    @SerializedName("itemsPurchased")
    val itemsPurchased: Int? = null,
    @SerializedName("killingSprees")
    val killingSprees: Int? = null,
    @SerializedName("kills")
    val kills: Int? = null,
    @SerializedName("lane")
    val lane: String? = null,
    @SerializedName("largestCriticalStrike")
    val largestCriticalStrike: Int? = null,
    @SerializedName("largestKillingSpree")
    val largestKillingSpree: Int? = null,
    @SerializedName("largestMultiKill")
    val largestMultiKill: Int? = null,
    @SerializedName("longestTimeSpentLiving")
    val longestTimeSpentLiving: Int? = null,
    @SerializedName("magicDamageDealt")
    val magicDamageDealt: Int? = null,
    @SerializedName("magicDamageDealtToChampions")
    val magicDamageDealtToChampions: Int? = null,
    @SerializedName("magicDamageTaken")
    val magicDamageTaken: Int? = null,
    @SerializedName("neutralMinionsKilled")
    val neutralMinionsKilled: Int? = null,
    @SerializedName("nexusKills")
    val nexusKills: Int? = null,
    @SerializedName("nexusLost")
    val nexusLost: Int? = null,
    @SerializedName("nexusTakedowns")
    val nexusTakedowns: Int? = null,
    @SerializedName("objectivesStolen")
    val objectivesStolen: Int? = null,
    @SerializedName("objectivesStolenAssists")
    val objectivesStolenAssists: Int? = null,
    @SerializedName("participantId")
    val participantId: Int? = null,
    @SerializedName("pentaKills")
    val pentaKills: Int? = null,
    @SerializedName("perks")
    val perks: Perks? = null,
    @SerializedName("physicalDamageDealt")
    val physicalDamageDealt: Int? = null,
    @SerializedName("physicalDamageDealtToChampions")
    val physicalDamageDealtToChampions: Int? = null,
    @SerializedName("physicalDamageTaken")
    val physicalDamageTaken: Int? = null,
    @SerializedName("profileIcon")
    val profileIcon: Int? = null,
    @SerializedName("puuid")
    val puuid: String? = null,
    @SerializedName("quadraKills")
    val quadraKills: Int? = null,
    @SerializedName("riotIdName")
    val riotIdName: String? = null,
    @SerializedName("riotIdTagline")
    val riotIdTagline: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("sightWardsBoughtInGame")
    val sightWardsBoughtInGame: Int? = null,
    @SerializedName("spell1Casts")
    val spell1Casts: Int? = null,
    @SerializedName("spell2Casts")
    val spell2Casts: Int? = null,
    @SerializedName("spell3Casts")
    val spell3Casts: Int? = null,
    @SerializedName("spell4Casts")
    val spell4Casts: Int? = null,
    @SerializedName("summoner1Casts")
    val summoner1Casts: Int? = null,
    @SerializedName("summoner1Id")
    val summoner1Id: Int? = null,
    @SerializedName("summoner2Casts")
    val summoner2Casts: Int? = null,
    @SerializedName("summoner2Id")
    val summoner2Id: Int? = null,
    @SerializedName("summonerId")
    val summonerId: String? = null,
    @SerializedName("summonerLevel")
    val summonerLevel: Int? = null,
    @SerializedName("summonerName")
    val summonerName: String? = null,
    @SerializedName("teamEarlySurrendered")
    val teamEarlySurrendered: Boolean? = null,
    @SerializedName("teamId")
    val teamId: Int? = null,
    @SerializedName("teamPosition")
    val teamPosition: String? = null,
    @SerializedName("timeCCingOthers")
    val timeCCingOthers: Int? = null,
    @SerializedName("timePlayed")
    val timePlayed: Int? = null,
    @SerializedName("totalDamageDealt")
    val totalDamageDealt: Int? = null,
    @SerializedName("totalDamageDealtToChampions")
    val totalDamageDealtToChampions: Int? = null,
    @SerializedName("totalDamageShieldedOnTeammates")
    val totalDamageShieldedOnTeammates: Int? = null,
    @SerializedName("totalDamageTaken")
    val totalDamageTaken: Int? = null,
    @SerializedName("totalHeal")
    val totalHeal: Int? = null,
    @SerializedName("totalHealsOnTeammates")
    val totalHealsOnTeammates: Int? = null,
    @SerializedName("totalMinionsKilled")
    val totalMinionsKilled: Int? = null,
    @SerializedName("totalTimeCCDealt")
    val totalTimeCCDealt: Int? = null,
    @SerializedName("totalTimeSpentDead")
    val totalTimeSpentDead: Int? = null,
    @SerializedName("totalUnitsHealed")
    val totalUnitsHealed: Int? = null,
    @SerializedName("tripleKills")
    val tripleKills: Int? = null,
    @SerializedName("trueDamageDealt")
    val trueDamageDealt: Int? = null,
    @SerializedName("trueDamageDealtToChampions")
    val trueDamageDealtToChampions: Int? = null,
    @SerializedName("trueDamageTaken")
    val trueDamageTaken: Int? = null,
    @SerializedName("turretKills")
    val turretKills: Int? = null,
    @SerializedName("turretTakedowns")
    val turretTakedowns: Int? = null,
    @SerializedName("turretsLost")
    val turretsLost: Int? = null,
    @SerializedName("unrealKills")
    val unrealKills: Int? = null,
    @SerializedName("visionScore")
    val visionScore: Int? = null,
    @SerializedName("visionWardsBoughtInGame")
    val visionWardsBoughtInGame: Int? = null,
    @SerializedName("wardsKilled")
    val wardsKilled: Int? = null,
    @SerializedName("wardsPlaced")
    val wardsPlaced: Int? = null,
    @SerializedName("win")
    val win: Boolean? = null
)