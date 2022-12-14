package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    var name: String,
    var valorPago: Double,
    var valorPagar: Double?,
    var valorReceber: Double?,
    var desc: String,
): Parcelable
