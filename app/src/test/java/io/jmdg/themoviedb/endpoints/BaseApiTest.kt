package io.jmdg.themoviedb.endpoints

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

/**
 * Freelancer Android SDK - BaseApiTest
 * More on: https://github.com/freelancer/freelancer-sdk-android/blob/master/android-sdk/src/test/kotlin/com/freelancer/android/sdk/endpoints/BaseApiTest.kt
 */
abstract class BaseApiTest {

    protected val server = MockWebServer()

    @Before
    fun start() {
        server.start()
    }

    @After
    fun shutDown() {
        server.shutdown()
    }

}

internal fun <T> Class<T>.getRetrofitApi(server: MockWebServer): T {
    val clientBuilder = OkHttpClient.Builder()

    val apiAdapter = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    return apiAdapter.create(this)
}

internal fun Any.readFromFile(file: String): String {
    val inputStream = this.javaClass.classLoader.getResourceAsStream(file)
    val result = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var length = inputStream.read(buffer)
    while (length != -1) {
        result.write(buffer, 0, length)
        length = inputStream.read(buffer)
    }
    return result.toString("UTF-8")
}

internal fun createMockResponse(body: String): MockResponse {
    return MockResponse().apply {
        setResponseCode(200)
        setBody(body)
        addHeader("Content-type: application/json")
    }
}