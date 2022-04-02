package io.radev.roman.domain

import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.data.PlacesRepository
import io.radev.roman.domain.model.NetworkStatus
import io.radev.shared.network.NetworkResponse
import io.radev.shared.network.model.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

/*
 * Created by Radoslaw on 22/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class GetPlacesUseCaseTest {

    @RelaxedMockK
    private lateinit var placesRepository: PlacesRepository

    @RelaxedMockK
    lateinit var dispatcher: CoroutineDispatcher

    private val mainThreadSurrogate = newSingleThreadContext("IO thread")

    private lateinit var useCase: GetPlacesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        useCase = GetPlacesUseCaseImpl(
            placesRepository = placesRepository,
            dispatcher = dispatcher
        )
        every { dispatcher.IO } returns mainThreadSurrogate
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when repository returns successful response then it is mapped correctly to the domain model `() =
        runTest {
            //Given
            val category = "cat"
            val lat = "lat"
            val lon = "lon"
            val mockkPlacesResponse = mockk<io.radev.shared.network.model.PlacesResponse>(relaxed = true)
            val mockkNetworkResponse =
                mockk<io.radev.shared.network.NetworkResponse.Success<io.radev.shared.network.model.PlacesResponse>>(relaxed = true) {
                    every { body } returns mockkPlacesResponse
                    every { body.results } returns listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                }

            //When
            coEvery {
                placesRepository.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkNetworkResponse

            val result = useCase.getPlaces(category = category, lat = lat, lon = lon)

            //Then
            Assert.assertTrue(result.networkStatus is NetworkStatus.Success)
            Assert.assertEquals(2, result.results.size)
            verify { dispatcher.IO }
        }

    @Test
    fun `when repository returns api error response then it is mapped correctly to the domain model`() =
        runTest {
            //Given
            val category = "cat"
            val lat = "lat"
            val lon = "lon"
            val responseErrorMsg = "error message"
            val mockkNetworkResponse = mockk<io.radev.shared.network.NetworkResponse.ApiError>(relaxed = true) {
                every { error?.toString() } returns responseErrorMsg
            }

            //When
            coEvery {
                placesRepository.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkNetworkResponse

            val result = useCase.getPlaces(category = category, lat = lat, lon = lon)

            //Then
            Assert.assertTrue(result.networkStatus is NetworkStatus.ApiError)
            Assert.assertEquals(
                responseErrorMsg,
                (result.networkStatus as NetworkStatus.ApiError).message
            )
            Assert.assertEquals(0, result.results.size)
            verify { dispatcher.IO }
        }

    @Test
    fun `when repository returns network error response then it is mapped correctly to the domain model`() =
        runTest {
            //Given
            val category = "cat"
            val lat = "lat"
            val lon = "lon"
            val mockkNetworkResponse = mockk<io.radev.shared.network.NetworkResponse.NetworkError>(relaxed = true)

            //When
            coEvery {
                placesRepository.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkNetworkResponse

            val result = useCase.getPlaces(category = category, lat = lat, lon = lon)

            //Then
            Assert.assertTrue(result.networkStatus is NetworkStatus.NetworkError)
            Assert.assertEquals(0, result.results.size)
            verify { dispatcher.IO }
        }

    @Test
    fun `when repository returns unknown error response then it is mapped correctly to the domain model`() =
        runTest {
            //Given
            val category = "cat"
            val lat = "lat"
            val lon = "lon"
            val responseErrorMsg = "error message"
            val mockkNetworkResponse = mockk<io.radev.shared.network.NetworkResponse.UnknownError>(relaxed = true) {
                every { error?.toString() } returns responseErrorMsg
            }

            //When
            coEvery {
                placesRepository.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkNetworkResponse

            val result = useCase.getPlaces(category = category, lat = lat, lon = lon)

            //Then
            Assert.assertTrue(result.networkStatus is NetworkStatus.UnknownError)
            Assert.assertEquals(
                responseErrorMsg,
                (result.networkStatus as NetworkStatus.UnknownError).message
            )
            Assert.assertEquals(0, result.results.size)
            verify { dispatcher.IO }
        }


}