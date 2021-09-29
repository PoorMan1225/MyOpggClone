package com.rjhwork.mycompany.opggcloneapp.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile

class SharedPreferenceManager(
    private val sharedPreferences: SharedPreferences
) : PreferenceManager {

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    override fun putSummonerProfile(key: String, value: SummonerProfile?) {
        if(value == null) {
            sharedPreferences.edit { putString(key, null) }
        } else {
            val gson = Gson()
            val json = gson.toJson(value)
            sharedPreferences.edit { putString(key, json) }
        }
    }

    override fun getSummonerProfile(key: String): SummonerProfile? {
        val gson = Gson()
        return sharedPreferences.getString(key, null)?.let {
            gson.fromJson(it, SummonerProfile::class.java)
        }
    }

    override fun getLong(key: String): Long = sharedPreferences.getLong(key, Long.MIN_VALUE)

    override fun putLong(key: String, value: Long) {
       sharedPreferences.edit {
           putLong(key, value)
       }
    }

    companion object {
        const val SUMMONER_PROFILE_KEY = "SUMMONER_PROFILE_KEY"
    }
}