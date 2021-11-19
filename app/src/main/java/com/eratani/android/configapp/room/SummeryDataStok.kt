package com.eratani.android.configapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class SummeryDataStok (

    @field:SerializedName("totalBarang")
    val totalBarang: Int? = 0,

    @field:SerializedName("totalUang")
    val totalUang: Float? = 0f,

    @field:SerializedName("totalStok")
    val totalStok: Int? = 0


) : Parcelable