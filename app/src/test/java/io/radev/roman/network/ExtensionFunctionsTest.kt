package io.radev.roman.network

import io.ktor.client.features.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class ExtensionFunctionsTest {

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test toNetworkResponse maps ServerResponseException to NetworkResponse UnknownError`() {
        //Given
        val exceptionMock = mockk<ServerResponseException>(relaxed = true)

        //When
        val result = exceptionMock.toNetworkResponse()

        //Then
        Assert.assertTrue(result is NetworkResponse.UnknownError)
        Assert.assertEquals(exceptionMock, (result as NetworkResponse.UnknownError).error)
    }

    @Test
    fun `test toNetworkResponse maps ClientRequestException to NetworkResponse ApiError`() {
        //Given
        val exceptionMock = mockk<ClientRequestException>(relaxed = true) {
            every { response.status.value } returns 422
        }

        //When
        val result = exceptionMock.toNetworkResponse()

        //Then
        Assert.assertTrue(result is NetworkResponse.ApiError)
        Assert.assertEquals(422, (result as NetworkResponse.ApiError).code)
        Assert.assertEquals(exceptionMock, (result as NetworkResponse.ApiError).error)
    }

    @Test
    fun `test toNetworkResponse maps RedirectResponseException to NetworkResponse UnknownError`() {
        //Given
        val exceptionMock = mockk<RedirectResponseException>(relaxed = true)

        //When
        val result = exceptionMock.toNetworkResponse()

        //Then
        Assert.assertTrue(result is NetworkResponse.UnknownError)
        Assert.assertEquals(exceptionMock, (result as NetworkResponse.UnknownError).error)
    }

    @Test
    fun `test toNetworkResponse maps IOException to NetworkResponse NetworkError`() {
        //Given
        val exceptionMock = mockk<IOException>(relaxed = true)

        //When
        val result = exceptionMock.toNetworkResponse()

        //Then
        Assert.assertTrue(result is NetworkResponse.NetworkError)
        Assert.assertEquals(exceptionMock, (result as NetworkResponse.NetworkError).error)
    }

    @Test
    fun `test toNetworkResponse maps UnknownHostException to NetworkResponse NetworkError`() {
        //Given
        val exceptionMock = mockk<UnknownHostException>(relaxed = true)

        //When
        val result = exceptionMock.toNetworkResponse()

        //Then
        Assert.assertTrue(result is NetworkResponse.NetworkError)
        Assert.assertEquals(exceptionMock, (result as NetworkResponse.NetworkError).error)
    }

    @Test
    fun `test toNetworkResponse maps any other exception to NetworkResponse UnknownError`() {
        //Given
        val exceptionMock = mockk<IllegalAccessException>(relaxed = true)

        //When
        val result = exceptionMock.toNetworkResponse()

        //Then
        Assert.assertTrue(result is NetworkResponse.UnknownError)
        Assert.assertEquals(exceptionMock, (result as NetworkResponse.UnknownError).error)
    }
}