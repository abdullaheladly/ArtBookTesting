package com.abdullah996.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullah996.artbooktesting.MainCoroutineRule
import com.abdullah996.artbooktesting.getOrAwaitValueTest
import com.abdullah996.artbooktesting.repo.FakeArtRepository
import com.abdullah996.artbooktesting.roomdb.Art
import com.abdullah996.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    /**this function means we don't wanna any threading here
     * make sure every thing is running in the same order
     */
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()


    /**simulate as if we are in the main thread
     * */
    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()


    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        // test doubles
        viewModel= ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `make an art without year returns error`(){
        viewModel.makeArt("Monalisa","ahmed","")
        //the last function converts livedata into regular data by the google code
       val value= viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `make an art without name returns error`() {
        viewModel.makeArt("","ahmed","1990")
        val value=viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }
    @Test
    fun `make an art without artist name returns error`() {
        viewModel.makeArt("cat","","1990")
        val value=viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `make an art with  not number year returns error`() {
        viewModel.makeArt("cat","ahmed","a")
        val value=viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}