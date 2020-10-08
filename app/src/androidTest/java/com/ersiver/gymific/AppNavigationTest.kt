package com.ersiver.gymific

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ersiver.gymific.repository.WorkoutRepository
import com.ersiver.gymific.util.DataBindingIdlingResource
import com.ersiver.gymific.util.EspressoIdlingResource
import com.ersiver.gymific.util.clickOnViewChild
import com.ersiver.gymific.util.monitorActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppNavigationTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: WorkoutRepository

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        hiltRule.inject()

        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun bottomNavigation_fromHomeToWorkout() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Workout screen.
        onView(withId(R.id.view_pager_fragment)).perform(click())

        // Check that Workout screen was opened.
        onView(withId(R.id.app_bar_workout)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun bottomNavigation_fromHomeToFavourite() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Favourite screen.
        onView(withId(R.id.navigation_favourite)).perform(click())

        // Check that Favourite screen was opened.
        onView(withId(R.id.app_bar_favourite)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun bottomNavigation_fromWorkoutToHome() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Workout screen.
        onView(withId(R.id.view_pager_fragment)).perform(click())

        // Navigate to Home Screen
        onView(withId(R.id.navigation_home)).perform(click())

        //Verify the Home screen was opened.
        onView(withId(R.id.app_bar_home)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun bottomNavigation_fromWorkoutToFavourite() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Workout screen.
        onView(withId(R.id.view_pager_fragment)).perform(click())

        // Navigate to Favourite Screen
        onView(withId(R.id.navigation_favourite)).perform(click())

        //Check that the Favourite screen was opened.
        onView(withId(R.id.app_bar_favourite)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun bottomNavigation_fromFavouriteToHome() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Favourite screen.
        onView(withId(R.id.navigation_favourite)).perform(click())

        // Navigate to Home Screen
        onView(withId(R.id.navigation_home)).perform(click())

        //Check that the Home screen was opened.
        onView(withId(R.id.app_bar_home)).check(matches(isDisplayed()))

        activityScenario.close()
    }


    @Test
    fun bottomNavigation_fromFavouriteToWorkout() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Favourite screen.
        onView(withId(R.id.navigation_favourite)).perform(click())

        // Navigate to Workout Screen
        onView(withId(R.id.view_pager_fragment)).perform(click())

        //Check that the Workout screen was opened.
        onView(withId(R.id.app_bar_workout)).check(matches(isDisplayed()))

        activityScenario.close()
    }


    @Test
    fun bottomNavigation_fromDetailToHome() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Detail screen by clicking on the child view in the list.
        onView(withId(R.id.recommended_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickOnViewChild(R.id.nav_detail_button)
            )
        )

        //Navigate to Home Screen
        onView(withId(R.id.navigation_home)).perform(click())

        //Check that the Home screen was opened.
        onView(withId(R.id.app_bar_home)).check(matches(isDisplayed()))

        activityScenario.close()
    }


    @Test
    fun bottomNavigation_fromDetailToWorkout() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Detail screen by clicking on the child view in the list
        onView(withId(R.id.recommended_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickOnViewChild(R.id.nav_detail_button)
            )
        )

        // Navigate to Workout Screen from Detail.
        onView(withId(R.id.view_pager_fragment)).perform(click())

        //Check that the Workout screen was opened.
        onView(withId(R.id.app_bar_workout)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun bottomNavigation_fromDetailToFavourite() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Detail screen by clicking on the child view in the list
        onView(withId(R.id.recommended_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickOnViewChild(R.id.nav_detail_button)
            )
        )

        // Navigate to Favourite Screen from Detail.
        onView(withId(R.id.navigation_favourite)).perform(click())

        //Check that the Favourite screen was opened.
        onView(withId(R.id.app_bar_favourite)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun homeScreen_detailScreen__upButton_backToHome() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Detail screen by clicking on the child view in the list
        onView(withId(R.id.recommended_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickOnViewChild(R.id.nav_detail_button)
            )
        )

        //Verify navigation to Detail.
        onView(withId(R.id.app_bar_detail)).check(matches(isDisplayed()))

        //Click UpButton in the Detail screen.
        onView(withContentDescription("Navigate up")).perform(click())

        //Verify navigation to back to Home screen.
        onView(withId(R.id.app_bar_home)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun workoutScreen_detailScreen__upButton_backToWorkout() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Workouts screen.
        onView(withId(R.id.view_pager_fragment)).perform(click())

        //Navigate to Detail screen by clicking on the workout on the list
        onView(withId(R.id.cardio_list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        //Verify navigation to Detail.
        onView(withId(R.id.app_bar_detail)).check(matches(isDisplayed()))

        //Click UpButton in the Detail screen.
        onView(withContentDescription("Navigate up")).perform(click())

        //Verify navigation to back to Workout screen.
        onView(withId(R.id.app_bar_workout)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun workoutScreen_backButton_navigateToHomeScreen() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Workout screen.
        onView(withId(R.id.view_pager_fragment)).perform(click())

        //Press system back button.
        pressBack()

        //Check that the Home screen was opened.
        onView(withId(R.id.app_bar_home)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun favouriteScreen_backButton_navigateToHomeScreen() {
        //Start up Home Screen.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //Navigate to Favourite screen.
        onView(withId(R.id.navigation_favourite)).perform(click())

        //Press system back button.
        pressBack()

        //Check that the Home screen was opened.
        onView(withId(R.id.app_bar_home)).check(matches(isDisplayed()))

        activityScenario.close()
    }
}