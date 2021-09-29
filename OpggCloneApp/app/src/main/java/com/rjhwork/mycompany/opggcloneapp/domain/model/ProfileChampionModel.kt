package com.rjhwork.mycompany.opggcloneapp.domain.model

import java.util.Comparator
import kotlin.math.roundToInt

class ProfileChampionModel(
    val championName: String?,
    var count: Int = 1,
) {
    var championIcon: String? = null

    var winFlag: Boolean = false
        set(value) {
            if (value)
                winCount = 1
            field = value
        }

    var winRate: Float = 0f
        set(value) {
            field = (value * 1000).roundToInt() / 10f
        }

    var winCount: Int = 0
        set(value) {
            field += value
            winRate = field / count.toFloat()
        }

    var kill: Int = 0
        set(value) {
            field += value
            kda = if (death > 0) {
                (kill + assist) / death.toFloat()
            } else {
                100.0f
            }
        }

    var death: Int = 0
        set(value) {
            field += value
            kda = if (death > 0) {
                (kill + assist) / death.toFloat()
            } else {
                100.0f
            }
        }

    var assist: Int = 0
        set(value) {
            field += value
            kda = if (death > 0) {
                (kill + assist) / death.toFloat()
            } else {
                100.0f
            }
        }

    var kda: Float = 0.0f
        set(value) {
            field = (value * 100).roundToInt() / 100f
        }

    override fun equals(other: Any?): Boolean {
        if (other !is ProfileChampionModel) return false
        if (championName != other.championName) return false
        return true
    }

    override fun hashCode(): Int {
        return 31 * championName.hashCode()
    }

    object CountComparator : Comparator<ProfileChampionModel> {
        override fun compare(o1: ProfileChampionModel, o2: ProfileChampionModel): Int =
            when {
                o1.count > o2.count -> -1
                o1.count < o2.count -> 1
                else -> 0
            }
    }
}