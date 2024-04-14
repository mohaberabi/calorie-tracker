package com.example.calroiestracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.core.domain.prefs.Prefs
import com.example.core.domain.use_case.FilterDigits
import com.example.data.prefs.DefaultPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSharedPrefs(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePrefs(prefs: SharedPreferences): Prefs {
        return DefaultPrefs(prefs)
    }


    @Provides
    @Singleton
    fun provideFilterDigit(): FilterDigits {
        return FilterDigits()
    }
}