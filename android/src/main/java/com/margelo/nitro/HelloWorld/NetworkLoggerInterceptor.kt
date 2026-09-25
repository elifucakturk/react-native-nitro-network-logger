package com.margelo.nitro.HelloWorld

import okhttp3.Interceptor
import okhttp3.Response

class NetworkLoggerInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val response = chain.proceed(request)

        val requestLog = """
            REQUEST: ${request.method} ${request.url}
            RESPONSE: ${response.code}
            Status: ${response.code}
            Response başarıyla alındı.
        """.trimIndent()

        HybridHelloWorld.instance?.appendLog(requestLog)

        return response
    }
}