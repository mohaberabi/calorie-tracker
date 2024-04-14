package com.example.tracker_data.repository

import com.example.tracker_data.local.dao.TrackerDao
import com.example.tracker_data.mapper.toTrackableFood
import com.example.tracker_data.mapper.toTrackedFood
import com.example.tracker_data.mapper.toTrackedFoodEntity
import com.example.tracker_data.remote.OpenFoodApi
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val trackerDao: TrackerDao, private val api: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pages: Int
    ): Result<List<TrackableFood>> {
        return try {
            val res = api.searchFood(query, page, pages)
            Result.success(res.products.mapNotNull { prod ->
                prod.toTrackableFood()
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertFood(food: TrackedFood) {


        trackerDao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteFood(food: TrackedFood) {
        trackerDao.deleteTrackedFood(food.toTrackedFoodEntity())

    }

    override fun getFoodForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return trackerDao.getFoodByDate(
            day = date.dayOfMonth,
            month = date.month.value,
            year = date.year
        ).map { items ->
            items.map { item ->
                item.toTrackedFood()
            }
        }
    }
}