package com.example.calroiestracker

import android.widget.SearchView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calroiestracker.repository.FakeTrackerRepository
import com.example.calroiestracker.ui.theme.CalroiesTrackerTheme
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.prefs.Prefs
import com.example.core.domain.use_case.FilterDigits
import com.example.core.navigation.Route
import com.example.onboarding_presentation.activity.ActivityScreen
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.goal.GoalScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.nutri_goals.NutriGoalScreen
import com.example.onboarding_presentation.weight.WeightScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.use_case.CalcMealNutriUSeCase
import com.example.tracker_domain.use_case.DeleteFoodUseCase
import com.example.tracker_domain.use_case.GetFoodByDateUseCase
import com.example.tracker_domain.use_case.SearchFoodUseCase
import com.example.tracker_domain.use_case.TrackFoodUseCase
import com.example.tracker_domain.use_case.TrackerUseCases
import com.example.tracker_presentation.overview.TrackerOverviewViewModel
import com.example.tracker_presentation.overview.TrackerScreen
import com.example.tracker_presentation.search.SearchScreen
import com.example.tracker_presentation.search.SearchViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt
import com.google.common.truth.Truth.assertThat
import io.mockk.InternalPlatformDsl.toStr

@OptIn(ExperimentalComposeUiApi::class)
@HiltAndroidTest
class TrackerOverviewE2E {


    // rule to inject the depend hilt used to need in the real app

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var fakeRepo: FakeTrackerRepository
    private lateinit var prefs: Prefs
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var overviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var navController: NavHostController


    @Before
    fun setup() {

        prefs = mockk(relaxed = true)

        every { prefs.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 20,
            carb = 20,
            fat = 20,
            level = ActivityLevel.High,
            protein = 20,
            goal = GoalType.LoseWeight,
            height = 100,
        )


        fakeRepo = FakeTrackerRepository()
        trackerUseCases = TrackerUseCases(
            trackFood = TrackFoodUseCase(fakeRepo),
            searchFood = SearchFoodUseCase(fakeRepo),
            getFoodByDateUseCase = GetFoodByDateUseCase(fakeRepo),
            deleteFoodUseCase = DeleteFoodUseCase(fakeRepo),
            calcMealNutriUSeCase = CalcMealNutriUSeCase(prefs),
        )
        overviewViewModel =
            TrackerOverviewViewModel(useCases = trackerUseCases, prefs = prefs)
        searchViewModel = SearchViewModel(trackerUseCases, FilterDigits())
        composeRule.setContent {


            CalroiesTrackerTheme {
                val scaffoldState = rememberScaffoldState()
                navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
                ) { padd ->
                    Box(modifier = Modifier.padding(padd)) {
                        NavHost(
                            navController = navController,
                            startDestination = Route.TRACKER_OVERVIEW
                        ) {

                            composable(Route.TRACKER_OVERVIEW) {

                                TrackerScreen(

                                    viewModel = overviewViewModel,
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
                                    viewModel = searchViewModel,
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

    @Test
    fun addBreakFastAppearsUnderBreakfastWithNutriCalculatedCorrect() {
        fakeRepo.setSearchList(
            listOf(
                TrackableFood(
                    name = "name",
                    imageUrl = null,
                    calPer100g = 150,
                    proteinPer100g = 5,
                    fatPer100g = 1,
                    carbPer100g = 100,
                )
            )
        )

        val addedAmount = 150

        val expectedCal = (1.5f * 150).roundToInt()
        val expecetedProt = (1.5f * 5).roundToInt()
        val expectedFat = (1.5f * 1).roundToInt()
        val excpectedCarb = (1.5f * 50).roundToInt()

        composeRule.onNodeWithText("Add Breakfast").assertDoesNotExist()
        composeRule.onNodeWithText("Breakfast").performClick()

        composeRule.onNodeWithText("Add Breakfast").assertIsDisplayed()
        composeRule.onNodeWithText("Add Breakfast").performClick()
        assertThat(navController.currentDestination?.route?.startsWith(Route.SEARCH)).isTrue()

        composeRule.onNodeWithTag("searchFilled")
            .assertIsDisplayed()
        composeRule.onNodeWithTag("searchFilled")
            .performTextInput("qqqq")

        composeRule.onNodeWithContentDescription("Search")
            .performClick()

        composeRule.onNodeWithText("Carbs")
            .performClick()

        composeRule.onNodeWithTag("amount")
            .performTextInput(addedAmount.toStr())
        composeRule.onNodeWithContentDescription("Track")
            .performClick()


        assertThat(navController.currentDestination?.route?.startsWith(Route.TRACKER_OVERVIEW))
            .isTrue()

        composeRule.onAllNodesWithText(excpectedCarb.toStr())
            .onFirst()
            .assertIsDisplayed()

        composeRule.onAllNodesWithText(expecetedProt.toStr())
            .onFirst()
            .assertIsDisplayed()
        composeRule.onAllNodesWithText(expectedFat.toStr())
            .onFirst()
            .assertIsDisplayed()

    }


}