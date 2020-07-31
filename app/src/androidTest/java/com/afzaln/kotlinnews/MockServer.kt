package com.afzaln.kotlinnews

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStreamReader

class MockServer {
    private var mockWebServer = MockWebServer()

    fun start() {
        mockWebServer.start(8080)
    }

    fun shutdown() {
        mockWebServer.shutdown()
    }

    fun queueError() {
        mockWebServer.dispatcher = ErrorDispatcher()
    }

    fun queueSuccess() {
        mockWebServer.dispatcher = SuccessDispatcher()
    }

    class SuccessDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            val body = readStringFromFile("api/success_response.json")
            if (request.path.equals("/r/kotlin/.json", true)) {
                return MockResponse().setResponseCode(200).setBody(body)
            }
            return MockResponse().setResponseCode(400)
        }
    }

    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse = MockResponse().setResponseCode(400)
    }
}

fun readStringFromFile(fileName: String): String {
    try {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    } catch (e: IOException) {
        throw e
    }
}
