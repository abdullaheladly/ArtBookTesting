package com.abdullah996.artbooktesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.abdullah996.artbooktesting.R
import com.abdullah996.artbooktesting.getOrAwaitValue
import com.abdullah996.artbooktesting.launchFragmentInHiltContainer
import com.abdullah996.artbooktesting.repo.FakeArtRepositoryAndroid
import com.abdullah996.artbooktesting.roomdb.Art
import com.abdullah996.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsFragmentToImageApiFragment(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
    }
    @Test
    fun testOnBackPressed(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }
    @Test
    fun testSave(){
        val testViewMode=ArtViewModel(FakeArtRepositoryAndroid())
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            viewModel=testViewMode
        }
        Espresso.onView(withId(R.id.nameText)).perform(ViewActions.replaceText("dog"))
        Espresso.onView(withId(R.id.artistText)).perform(ViewActions.replaceText("Ahmed"))
        Espresso.onView(withId(R.id.yearText)).perform(ViewActions.replaceText("1996"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())
        assertThat(testViewMode.artList.getOrAwaitValue()).contains(
            Art("dog","Ahmed",1996,"")
        )

    }
}