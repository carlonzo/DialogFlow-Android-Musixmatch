package com.carlom.musixmatch.example.demo.controllers

import ai.api.GsonFactory
import ai.api.model.AIResponse
import ai.api.model.Fulfillment
import com.carlom.musixmatch.example.demo.presenter.LyricsPresenter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger


@RestController
@RequestMapping("/dialogflow")
class MainEndpoint @Autowired constructor(val lyricsPresenter: LyricsPresenter) {

    private val gson = GsonFactory.getDefaultFactory().gson

    @RequestMapping("/request", method = [RequestMethod.POST])
    fun handleRequest(@RequestBody rawRequest: String): String {

        val aiResponse = gson.fromJson(rawRequest, AIResponse::class.java)
        Logger.getLogger("handleRequest").log(Level.INFO, aiResponse.toString())

        val lyrics = lyricsPresenter.getLyrics(aiResponse)

        val fulfillment = Fulfillment()
        fulfillment.speech = "Here is the lyrics"
        fulfillment.displayText = lyrics

        return gson.toJson(fulfillment)
    }

}