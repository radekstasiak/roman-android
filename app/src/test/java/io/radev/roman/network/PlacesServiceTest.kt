package io.radev.roman.network

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.mockk.verify
import io.radev.roman.network.testdata.JSON_PLACES_200
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesServiceTest {

    private lateinit var serviceImpl: PlacesServiceImpl

    @Before
    fun setup() {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(JSON_PLACES_200),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val apiClient = ApiClient(
            engine = mockEngine,
            apiKey = "apiKey"
        )

        serviceImpl = PlacesServiceImpl(apiClient = apiClient)
    }

    @Test
    fun `test 200 response is serialised to PlacesResponse correctly`() = runBlocking {
        //When
        val result = serviceImpl.getPlaces("restaurants", "1.0", "1.1")
verify { }
        //Then
        Assert.assertEquals(1, result.results.size)
        val place = result.results[0]
        Assert.assertEquals("5c01828d364d9700395904e9", place.fsqId)
        Assert.assertEquals("Assembly Underground", place.name)
        Assert.assertEquals("13065", place.categories[0].id)

    }


}