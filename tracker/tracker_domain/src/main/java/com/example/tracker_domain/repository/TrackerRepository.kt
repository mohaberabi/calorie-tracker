package com.example.tracker_domain.repository

import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pages: Int,
    ): Result<List<TrackableFood>>

    suspend fun insertFood(food: TrackedFood)
    suspend fun deleteFood(food: TrackedFood)
    fun getFoodForDate(date: LocalDate): Flow<List<TrackedFood>>

}