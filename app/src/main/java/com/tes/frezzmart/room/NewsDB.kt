package com.tes.frezzmart.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(NewsModel::class), version = 1, exportSchema = false)
abstract class NewsDB : RoomDatabase() {
    abstract val newsDao: NewsDao
}