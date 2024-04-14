package com.example.tracker_data.local.dao

import androidx.room.*
import com.example.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TrackedFoodEntity::class)
    fun insertTrackedFood(food: TrackedFoodEntity)

    @Delete
    fun deleteTrackedFood(food: TrackedFoodEntity)

    @Query(
        """
            SELECT *
            FROM trackedfoodentity
            WHERE dayOfMonth = :day AND month = :month AND year = :year
        """
    )
    fun getFoodByDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>

}