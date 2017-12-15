package com.carlom.musixmatch.example.demo.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LyricsResponseApi {

    @SerializedName("message")
    var message: LyricsResponseMessage? = null


    fun getLyrics(): Lyrics {
        return message!!.body!!.lyrics!!
    }


}

class LyricsResponseMessage {
    @SerializedName("body")
    var body: LyricsResponseBody? = null
}

class LyricsResponseBody {

    @SerializedName("lyrics")
    @Expose
    var lyrics: Lyrics? = null

}

class Lyrics {

    @SerializedName("lyrics_id")
    var lyricsId: Int? = null

    @SerializedName("lyrics_body")
    var lyricsBody: String? = null


}
