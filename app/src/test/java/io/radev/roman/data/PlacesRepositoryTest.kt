package io.radev.roman.data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.network.NetworkResponse
import io.radev.roman.network.PlacesService
import io.radev.roman.network.model.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesRepositoryTest {

    private val mainThreadSurrogate = newSingleThreadContext("IO thread")
    lateinit var repository: PlacesRepository

    @RelaxedMockK
    lateinit var service: PlacesService

    @RelaxedMockK
    lateinit var dispatcher: CoroutineDispatcher

    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        repository = PlacesRepositoryImpl(
            placesService = service,
            dispatcher = dispatcher
        )
//        coEvery { dispatcher.IO } returns testDispatcher

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when user gets places then successful response is returned correctly`() = runBlockingTest {
        //Given
        val placesResponseMock = mockk<PlacesResponse>(relaxed = true)

        coEvery {
            service.getPlaces(
                category = "cat",
                lat = "lat",
                lon = "lon"
            )
        } returns placesResponseMock

        //When
        val result = repository.getPlaces(category = "cat", lat = "lat", lon = "lon")
        Assert.assertTrue(result is NetworkResponse.Success)
        Assert.assertEquals(placesResponseMock, (result as NetworkResponse.Success).body)
    }

}