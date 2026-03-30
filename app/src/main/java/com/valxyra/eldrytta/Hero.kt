package com.valxyra.eldrytta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    val name: String?,
    val herotitle: String?,
    val description: String?,
    val race: String?,
    val jobclass: String?,
    val region: String?,
    val skill: String?,
    val equipment: String?,
    val photo: Int
) : Parcelable