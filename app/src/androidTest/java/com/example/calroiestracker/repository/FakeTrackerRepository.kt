package com.example.calroiestracker.repository

import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import java.util.Random

class FakeTrackerRepository : TrackerRepository {

    private val foods = mutableListOf<TrackedFood>()
    private var searchResults = listOf<TrackableFood>()


    private val foodFlow = MutableSharedFlow<List<TrackedFood>>(replay = 1)
    private var shouldReturnError = false
    fun setSearchList(list: List<TrackableFood>) {
        searchResults = list
    }

    override suspend fun searchFood(
        query: String,
        page: Int,
        pages: Int
    ): Result<List<TrackableFood>> {

        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(searchResults)
        }
    }

    override suspend fun insertFood(food: TrackedFood) {
        foods.add(food.copy(id = Random().nextInt()))

    }

    override suspend fun deleteFood(food: TrackedFood) {
        foods.remove(food)

    }

    override fun getFoodForDate(date: LocalDate): Flow<List<TrackedFood>> = foodFlow

}