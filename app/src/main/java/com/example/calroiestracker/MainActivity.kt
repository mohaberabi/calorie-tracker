package com.example.calroiestracker

import com.example.onboarding_presentation.weight.WeightScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.calroiestracker.ui.theme.CalroiesTrackerTheme
import com.example.core.domain.prefs.Prefs
import com.example.core.navigation.Route
import com.example.onboarding_presentation.activity.ActivityScreen
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.goal.GoalScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.nutri_goals.NutriGoalScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import com.example.tracker_presentation.overview.TrackerScreen
import com.example.tracker_presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefs: Prefs

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val showOnBoarding = prefs.loadOnBoarding()
        setContent {
            CalroiesTrackerTheme {
                val navController = rememberNavController()

                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
                ) { padd ->
                    Box(modifier = Modifier.padding(padd)) {
                        NavHost(
                            navController = navController,
                            startDestination = if (showOnBoarding) Route.WELCOME else Route.TRACKER_OVERVIEW
                        ) {

                            composable(Route.WELCOME) {
                                WelcomeScreen(
                                    onNextClick = {
                                        navController.navigate(Route.GENDER)
                                    }
                                )

                            }
                            composable(Route.GENDER) {
                                GenderScreen(
                                    onNextClick = {
                                        navController.navigate(Route.AGE)
                                    }
                                )

                            }
                            composable(Route.AGE) {
                                AgeScreen(
                                    scaffoldState = scaffoldState,
                                    onNextClick = {
                                        navController.navigate(Route.WEIGHT)
                                    }
                                )
                            }
                            composable(Route.WEIGHT) {
                                WeightScreen(
                                    scaffoldState = scaffoldState,
                                    onNextClick = {
                                        navController.navigate(Route.GENDER)
                                    }

                                )

                            }
                            composable(Route.HEIGHT) {
                                HeightScreen(
                                    scaffoldState = scaffoldState,
                                    onNextClick = {
                                        navController.navigate(Route.ACTIVITY)
                                    }
                                )
                            }
                            composable(Route.ACTIVITY) {
                                ActivityScreen(


                                    onNextClick = {
                                        navController.navigate(Route.GOALS)
                                    }


                                )
                            }

                            composable(Route.GOALS) {

                                GoalScreen(
                                    onNextClick = {
                                        navController.navigate(Route.NUTRI_GOALS)
                                    }


                                )
                            }


                            composable(Route.NUTRI_GOALS) {

                                NutriGoalScreen(
                                    scaffoldState = scaffoldState,
                                    onNextClick = {
                                        navController.navigate(Route.TRACKER_OVERVIEW)

                                    }
                                )
                            }
                            composable(Route.TRACKER_OVERVIEW) {


                                TrackerScreen(
                                    onSearchRequest = {

                                            name, day, month, year ->
                                        navController.navigate(
                                            route = Route.SEARCH + "/{$name}/{$day}/{$month}/{$year}",
                                        )

                                    }
                                )

                            }


                            composable(
                                route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                                arguments = listOf(
                                    navArgument("mealName") {

                                        type = NavType.StringType
                                    },
                                    navArgument("dayOfMonth") {

                                        type = NavType.IntType
                                    },
                                    navArgument("month") {

                                        type = NavType.IntType
                                    },
                                    navArgument("year") {

                                        type = NavType.IntType
                                    },
                                )
                            ) {

                                val mealName = it.arguments?.getString("mealName")!!
                                val day = it.arguments?.getInt("day")!!
                                val month = it.arguments?.getInt("dayOfMonth")!!
                                val year = it.arguments?.getInt("year")!!

                                SearchScreen(
                                    scaffoldState = scaffoldState,
                                    mealName = mealName,
                                    dayOfMonth = day,
                                    month = month,
                                    year = year,
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    })
                            }

                        }
                    }

                }

            }
        }
    }
}

