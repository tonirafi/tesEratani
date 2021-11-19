package com.eratani.android.configapp.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ProdukModel::class], version = 1, exportSchema = false)
abstract class ProdukDB : RoomDatabase() {
    abstract val produkDao: ProdukDao
}