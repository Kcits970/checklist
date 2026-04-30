package com.example.dailybloom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject

class UserRepository {
    companion object {
        private val JSON: MediaType = "application/json".toMediaType()
        private val client: OkHttpClient by lazy { OkHttpClient() }
        private const val BASE_URL = "http://10.0.2.2:8080"
        /*
        IMPORTANT NOTE:
        The ip portion of BASE_URL should be 10.0.2.2 if you are using the emulator.
        Otherwise, make sure that your device is connected under the same network as the host, and use the host's local ip.
        Please be cautious to not commit code containing your local ip.
         */
    }

    suspend fun queryUser(email: String): User? = withContext(Dispatchers.IO) {
        val body = buildJsonBody("login_info" to email)
        val request = buildPostRequest("$BASE_URL/login", body)

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) parseLoginResponse(response, email)
                else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun buildJsonBody(vararg pairs: Pair<String, String>): RequestBody {
        val json = JSONObject().apply {
            pairs.forEach { (key, value) -> put(key, value) }
        }.toString()
        return json.toRequestBody(JSON)
    }

    private fun buildPostRequest(url: String, body: RequestBody): Request {
        return Request.Builder()
            .url(url)
            .post(body)
            .build()
    }

    private fun parseLoginResponse(response: Response, email: String): User? {
        val jsonObject = JSONObject(response.body.string())
        return if (jsonObject.optInt("status") == 0) {
            User(
                id = jsonObject.optInt("id"),
                email = email,
                nickname = jsonObject.optString("nickname")
            )
        } else null
    }
}