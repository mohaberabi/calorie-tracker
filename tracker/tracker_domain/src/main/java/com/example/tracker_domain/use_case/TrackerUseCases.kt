package com.example.tracker_domain.use_case

data class TrackerUseCases(
    val trackFood: TrackFoodUseCase,
    val searchFood: SearchFoodUseCase,
    val getFoodByDateUseCase: GetFoodByDateUseCase,
    val deleteFoodUseCase: DeleteFoodUseCase,
    val calcMealNutriUSeCase: CalcMealNutriUSeCase,
)
