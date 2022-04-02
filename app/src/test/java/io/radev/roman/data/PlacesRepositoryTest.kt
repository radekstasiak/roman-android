package io.radev.roman.data

import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.radev.roman.CoroutineDispatcher
import io.radev.shared.data.PlacesRepository
import io.radev.shared.data.PlacesRepositoryImpl
import io.radev.shared.network.toNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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
    lateinit var service: io.radev.shared.network.PlacesService

    @RelaxedMockK
    lateinit var dispatcher: CoroutineDispatcher


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic("io.radev.shared.network.NetworkResponseKt")
        Dispatchers.setMain(mainThreadSurrogate)
        repository = PlacesRepositoryImpl(
            placesService = service,
//            dispatcher = dispatcher
        )
        every { dispatcher.IO } returns mainThreadSurrogate

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when user gets places then successful response is returned correctly`() = runTest {
        //Given
        val placesResponseMock = mockk<io.radev.shared.network.model.PlacesResponse>(relaxed = true)

        coEvery {
            service.getPlaces(
                category = "cat",
                lat = "lat",
                lon = "lon"
            )
        } returns placesResponseMock

        //When
        val result = repository.getPlaces(category = "cat", lat = "lat", lon = "lon")
        Assert.assertTrue(result is io.radev.shared.network.NetworkResponse.Success)
        Assert.assertEquals(placesResponseMock, (result as io.radev.shared.network.NetworkResponse.Success).body)
//        verify { dispatcher.IO }
    }

    @Test
    fun `when getPlaces returns exception its wrapped to NetworkResponse`() = runTest {
        //Given
        val exceptionMockk = mockk<Exception>(relaxed = true)
        coEvery {
            service.getPlaces(
                category = "cat",
                lat = "lat",
                lon = "lon"
            )
        } throws exceptionMockk

        every { exceptionMockk.toNetworkResponse() } returns io.radev.shared.network.NetworkResponse.UnknownError(
            exceptionMockk
        )
        //When
        val result = repository.getPlaces(category = "cat", lat = "lat", lon = "lon")
        Assert.assertTrue(result is io.radev.shared.network.NetworkResponse.UnknownError)
        Assert.assertEquals(exceptionMockk, (result as io.radev.shared.network.NetworkResponse.UnknownError).error)
//        verify { dispatcher.IO }
    }

}