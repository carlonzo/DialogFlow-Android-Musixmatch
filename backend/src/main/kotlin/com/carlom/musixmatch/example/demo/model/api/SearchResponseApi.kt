package com.carlom.musixmatch.example.demo.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResponseApi {

    @SerializedName("message")
    var message: SearchResponseApiMessage? = null

    fun getTrackList(): List<TrackItem> {
        return message!!.body!!.trackList!!
    }

}

class SearchResponseApiMessage {
    @SerializedName("body")
    var body: SearchResponseBody? = null
}

class SearchResponseBody {

    @SerializedName("track_list")
    var trackList: List<TrackItem>? = null

}

class TrackItem {
    @SerializedName("track")
    var track: Track? = null
}

class Track {

    @SerializedName("track_id")
    var trackId: Int? = null

    @SerializedName("track_name")
    var trackName: String? = null

    @SerializedName("track_rating")
    var trackRating: Int? = null

    @SerializedName("track_length")
    var trackLength: Int? = null

    @SerializedName("has_lyrics")
    var hasLyrics: Int? = null

    @SerializedName("lyrics_id")
    var lyricsId: Int? = null

    @SerializedName("album_id")
    var albumId: Int? = null

    @SerializedName("album_name")
    var albumName: String? = null

    @SerializedName("artist_id")
    var artistId: Int? = null

    @SerializedName("artist_name")
    var artistName: String? = null

}
