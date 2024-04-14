package com.example.onboarding_domain.di

import com.example.onboarding_domain.use_case.ValidateNutriUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OnBoardingDomainModule {
    @Provides
    @Singleton
    fun provideValidateNutriUseCase(): ValidateNutriUseCase {
        return ValidateNutriUseCase()
    }
}