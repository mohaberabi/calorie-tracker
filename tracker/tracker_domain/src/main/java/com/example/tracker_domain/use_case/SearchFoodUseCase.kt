package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.repository.TrackerRepository

class SearchFoodUseCase(private val trackerRepository: TrackerRepository) {


    suspend operator fun invoke(
        query: String, page: Int, pages: Int
    ): Result<List<TrackableFood>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return trackerRepository.searchFood(query, page, pages)
    }
}