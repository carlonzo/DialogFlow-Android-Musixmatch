package com.carlom.musixmatch.example.demo.presenter


import ai.api.model.AIResponse
import com.carlom.musixmatch.example.demo.model.api.LyricsResponseApi
import com.carlom.musixmatch.example.demo.model.api.SearchResponseApi
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LyricsPresenter @Autowired constructor(private val httpClient: HttpClient) {

    private val searchUrl = HttpUrl.parse("https://api.musixmatch.com/ws/1.1/track.search")!!
    private val lyricsUrl = HttpUrl.parse("https://api.musixmatch.com/ws/1.1/track.lyrics.get")!!
    private val apiKey = "ADD API KEY"

    fun getLyrics(response: AIResponse): String {

        if (response.isError) {
            return ""
        }

        val parameters = response.result.parameters

        val trackRequest = createTrackRequest(parameters["track"]!!.asString, parameters["artist"]!!.asString)

        val trackId = retrieveTrackId(trackRequest)

        return retrieveLyrics(trackId)
    }

    private fun createTrackRequest(track: String, artist: String): Request {
        val urlSearchTrack = searchUrl.newBuilder()
                .addQueryParameter("format", "json")
                .addQueryParameter("q_track", track)
                .addQueryParameter("q_artist", artist)
                .addQueryParameter("apikey", apiKey)
                .build()


        return okhttp3.Request.Builder()
                .url(urlSearchTrack)
                .addHeader("Accept", "application/json")
                .get()
                .build()
    }

    private fun retrieveTrackId(trackRequest: Request): Int {
        val responseSearch = httpClient.client
                .newCall(trackRequest)
                .execute()

        val bodySearch = Gson().fromJson(responseSearch.body()!!.charStream(), SearchResponseApi::class.java)

        return bodySearch.getTrackList()[0].track!!.trackId!!
    }

    private fun retrieveLyrics(trackId: Int): String {
        val urlLyricsTrack = lyricsUrl.newBuilder()!!
                .addQueryParameter("format", "json")
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("track_id", trackId.toString())
                .build()

        val request = okhttp3.Request.Builder()
                .url(urlLyricsTrack)
                .addHeader("Accept", "application/json")
                .get()
                .build()

        val response = httpClient.client.newCall(request).execute()

        return Gson().fromJson(response.body()!!.charStream(), LyricsResponseApi::class.java)?.getLyrics()!!.lyricsBody!!
    }


}