package com.abdullah996.artbooktesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.abdullah996.artbooktesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)
    val exampleArt2 = Art("Mona Lisa","Da Vinci",1700,"test.com",2)
    val exampleArt3 = Art("Mona Lisa","Da Vinci",1700,"test.com",3)

    private lateinit var dao : ArtDao
    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    @Before
    fun setup() {
        //create database in Ram so we just use it for testing
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDatabase::class.java)
            .allowMainThreadQueries() //this is a test case, we don't want other thread pools
            .build()*/

        hiltRule.inject()
        dao = database.artDao()
    }
    @After
    fun teardown() {
        database.close()
    }

    //run blocking make sure that
    @Test
    fun insertArtTesting() = runBlockingTest {
        dao.insertArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)

    }
    @Test
    fun deleteArtTesting()= runBlockingTest {
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }

    @Test
    fun testObserveAtrsFun()= runBlockingTest{
        dao.insertArt(exampleArt)
        dao.insertArt(exampleArt2)
        dao.insertArt(exampleArt3)
        val list=dao.observeArts().getOrAwaitValue()
        assertThat(list.size).isGreaterThan(2)
    }

}