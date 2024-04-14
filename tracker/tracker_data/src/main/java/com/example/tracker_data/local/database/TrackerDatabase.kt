package com.example.tracker_data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracker_data.local.dao.TrackerDao
import com.example.tracker_data.local.entity.TrackedFoodEntity


@Database(
    entities = [TrackedFoodEntity::class],
    version = 1, exportSchema = false,
)
abstract class TrackerDatabase : RoomDatabase() {

    abstract val trackerDao: TrackerDao

}