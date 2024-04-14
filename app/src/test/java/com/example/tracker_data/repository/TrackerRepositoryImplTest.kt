package com.example.tracker_data.repository

import com.example.tracker_data.local.dao.TrackerDao
import com.example.tracker_data.remote.OpenFoodApi
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

import com.google.common.truth.Truth.assertThat

class TrackerRepositoryImplTest {


    private lateinit var repo: TrackerRepositoryImpl
    private lateinit var mockServer: MockWebServer
    private lateinit var okHttp: OkHttpClient
    private lateinit var api: OpenFoodApi


    @Before

    fun setup() {
        mockServer = MockWebServer()
        okHttp = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS).build()


        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttp).baseUrl(mockServer.url("/")).build()
            .create(OpenFoodApi::class.java)

        val dao = mockk<TrackerDao>(relaxed = true)

        repo = TrackerRepositoryImpl(
            trackerDao = dao,
            api = api
        )
    }


    @After
    fun tearDown() {
        mockServer.shutdown()

    }

    @Test
    fun `search food , valid response returns correct result `() = runBlocking {
//        mockServer.enqueue(
//            MockResponse().setResponseCode(200).setBody(MockValidApiResponse.validApiResponse)
//        )
//
//
//        val res = repo.searchFood("ASDSADsad", 1, 40)
//
//
//
//        assertThat(res.isSuccess).isTrue()
    }

    @Test
    fun `search food , valid response returns failure`() = runBlocking {
        mockServer.enqueue(
            MockResponse().setResponseCode(400).setBody("")
        )


        val res = repo.searchFood("ASDSADsad", 1, 40)



        assertThat(res.isFailure).isTrue()
    }
}