package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodByDateUseCase(private val trackerRepository: TrackerRepository) {


    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> =
        trackerRepository.getFoodForDate(date)
}