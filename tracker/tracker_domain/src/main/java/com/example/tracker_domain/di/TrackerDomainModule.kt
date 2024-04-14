package com.example.tracker_domain.di

import com.example.core.domain.prefs.Prefs
import com.example.tracker_domain.repository.TrackerRepository
import com.example.tracker_domain.use_case.CalcMealNutriUSeCase
import com.example.tracker_domain.use_case.DeleteFoodUseCase
import com.example.tracker_domain.use_case.GetFoodByDateUseCase
import com.example.tracker_domain.use_case.SearchFoodUseCase
import com.example.tracker_domain.use_case.TrackFoodUseCase
import com.example.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module

@InstallIn(SingletonComponent::class)
object TrackerDomainModule {


    @Singleton
    @Provides
    fun provideTrackerUseCase(
        trackerRepository: TrackerRepository,
        prefs: Prefs,
    ): TrackerUseCases {

        return TrackerUseCases(
            searchFood = SearchFoodUseCase(trackerRepository),
            deleteFoodUseCase = DeleteFoodUseCase(trackerRepository),
            getFoodByDateUseCase = GetFoodByDateUseCase(trackerRepository),
            calcMealNutriUSeCase = CalcMealNutriUSeCase(prefs),
            trackFood = TrackFoodUseCase(trackerRepository)

        )
    }

}