package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository

class DeleteFoodUseCase(private val trackerRepository: TrackerRepository) {


    suspend operator fun invoke(food: TrackedFood) = trackerRepository.deleteFood(food)

}