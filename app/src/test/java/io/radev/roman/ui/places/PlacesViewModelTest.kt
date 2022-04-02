package io.radev.roman.ui.places

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.domain.GetPlacesDomainModel
import io.radev.roman.domain.GetPlacesUseCase
import io.radev.roman.domain.model.NetworkStatus
import io.radev.shared.network.model.PlaceEntity
import io.radev.roman.ui.common.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
 * Created by Radoslaw on 22/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getPlacesUseCase: GetPlacesUseCase

    @RelaxedMockK
    private lateinit var dispatcher: CoroutineDispatcher

    private lateinit var viewModel: PlacesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel = PlacesViewModel(getPlacesUseCase, dispatcher)
        every { dispatcher.IO } returns testCoroutineDispatcher
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }


    @Test
    fun `test when use case returns successful response then it is mapped to ViewState correctly `() =
        runTest {
            //Given
            val category = "cat"
            val lat = "lat"
            val lon = "lon"

            val mockkDomainResponseModel = mockk<GetPlacesDomainModel>(relaxed = true) {
                every { networkStatus } returns mockk<NetworkStatus.Success>(relaxed = true) {
                    every { results } returns listOf(mockk<io.radev.shared.network.model.PlaceEntity>(relaxed = true) {
                        every { geocodes?.main } returns mockk(relaxed = true)
                    })
                }
            }
            //When
            coEvery {
                getPlacesUseCase.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkDomainResponseModel

            viewModel.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )

            //Then
            viewModel.uiState.test {
                assertEquals(ViewState.Loading, awaitItem())
                val loadedViewState = awaitItem()
                assertTrue(loadedViewState is ViewState.Loaded)
                assertEquals(
                    1,
                    (loadedViewState as ViewState.Loaded<List<PlaceUiModel>>).uiModel.size
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `test when use case returns NetworkError response then it is mapped to ViewState correctly `() =
        runTest {
            //Given
            val category = "restaurants"
            val lat = "53.801277"
            val lon = "-1.548567"
            val mockkDomainResponseModel = mockk<GetPlacesDomainModel>(relaxed = true) {
                every { networkStatus } returns mockk<NetworkStatus.NetworkError>(relaxed = true)
            }

            //When
            coEvery {
                getPlacesUseCase.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkDomainResponseModel

            viewModel.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )

            //Then
            viewModel.uiState.test {
                assertEquals(ViewState.Loading, awaitItem())
                assertEquals(ViewState.NoNetwork, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `test when use case returns ApiError response then it is mapped to ViewState correctly `() =
        runTest {
            //Given
            val category = "restaurants"
            val lat = "53.801277"
            val lon = "-1.548567"
            val mockkDomainResponseModel = mockk<GetPlacesDomainModel>(relaxed = true) {
                every { networkStatus } returns mockk<NetworkStatus.ApiError>(relaxed = true) {
                    every { message } returns "error message"
                }
            }

            //When
            coEvery {
                getPlacesUseCase.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkDomainResponseModel

            viewModel.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )

            //Then
            viewModel.uiState.test {
                assertEquals(ViewState.Loading, awaitItem())
                val apiErrorViewState = awaitItem()
                assertTrue(apiErrorViewState is ViewState.ApiError)
                assertEquals("error message", (apiErrorViewState as ViewState.ApiError).message)
                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `test when use case returns UnknownError response then it is mapped to ViewState correctly `() =
        runTest {
            //Given
            val category = "restaurants"
            val lat = "53.801277"
            val lon = "-1.548567"
            val mockkDomainResponseModel = mockk<GetPlacesDomainModel>(relaxed = true) {
                every { networkStatus } returns mockk<NetworkStatus.UnknownError>(relaxed = true) {
                    every { message } returns "error message"
                }
            }

            //When
            coEvery {
                getPlacesUseCase.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } returns mockkDomainResponseModel

            viewModel.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )

            //Then
            viewModel.uiState.test {
                assertEquals(ViewState.Loading, awaitItem())
                val unknownError = awaitItem()
                assertTrue(unknownError is ViewState.Error)
                assertEquals("error message", (unknownError as ViewState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `test when use case throws exception then it is handled to ViewState correctly `() =
        runTest {
            //Given
            val category = "restaurants"
            val lat = "53.801277"
            val lon = "-1.548567"
            val mockkException = mockk<Exception>(relaxed = true) {
                every { message } returns "error message"
            }


            //When
            coEvery {
                getPlacesUseCase.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
            } throws mockkException

            viewModel.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )

            //Then
            viewModel.uiState.test {
                assertEquals(ViewState.Loading, awaitItem())
                val unknownError = awaitItem()
                assertTrue(unknownError is ViewState.Error)
                assertEquals("error message", (unknownError as ViewState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `test toPlaceUiModel maps PlaceEntit to PlaceModelUi correctly`() {
        val placeEntity = io.radev.shared.network.model.PlaceEntity(
            name = "Name",
            location = io.radev.shared.network.model.PlaceEntity.Location(formattedAddress = "Formatted address"),
            categories = arrayListOf(
                io.radev.shared.network.model.PlaceEntity.Categories(
                    icon = io.radev.shared.network.model.PlaceEntity.Icon(
                        prefix = "prefix_",
                        suffix = "suffix"
                    )
                )
            ),
            geocodes = io.radev.shared.network.model.PlaceEntity.Geocodes(
                main = io.radev.shared.network.model.PlaceEntity.Geocodes.Main(
                    latitude = 1.0,
                    longitude = 2.0
                )
            )
        )
        val result = placeEntity.toPlaceUiModel()
        assertEquals("Name", result.name)
        assertEquals("Formatted address", result.address)
        assertEquals("prefix_suffix", result.icon)
        assertEquals(2.0, result.lon, 0.0)
        assertEquals(1.0, result.lat, 0.0)
        
    }
}

