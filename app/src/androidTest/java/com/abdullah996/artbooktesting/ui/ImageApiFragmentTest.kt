package com.abdullah996.artbooktesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.abdullah996.artbooktesting.R
import com.abdullah996.artbooktesting.adapter.ImageRecyclerAdapter
import com.abdullah996.artbooktesting.getOrAwaitValue
import com.abdullah996.artbooktesting.launchFragmentInHiltContainer
import com.abdullah996.artbooktesting.repo.FakeArtRepositoryAndroid
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
class ImageApiFragmentTest {

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @get:Rule
    var instaTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        val navController=Mockito.mock(NavController::class.java)
        //fake item to click on it
        val selectedImageUrl="abdullah.com"
        val selectedImageUrl2="abdullah2.com"
        val testViewModel=ArtViewModel(FakeArtRepositoryAndroid())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
            viewModel=testViewModel
            imageRecyclerAdapter.images= listOf(selectedImageUrl,selectedImageUrl2)
        }

        //simulate selecting item at position 1 in the recycle view
        Espresso.onView(withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                1,click()
            )
        )


        //check if it's move back after selecting or not
        Mockito.verify(navController).popBackStack()

        //check if it's the same as we passed
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl2)

    }
}