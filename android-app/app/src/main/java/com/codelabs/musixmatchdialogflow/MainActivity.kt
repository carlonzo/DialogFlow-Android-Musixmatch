package com.codelabs.musixmatchdialogflow

import ai.api.AIListener
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import android.Manifest
import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private val DIALOG_FLOW_ACCESS_TOKEN: String = "ADD DIALOG_FLOW_ACCESS_TOKEN"

    private lateinit var service: AIService
    private lateinit var textViewDebug: TextView
    private lateinit var micButton: View
    private var isRecordAudioPermissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDebug = findViewById(R.id.debugText)

        checkPermission()

        initAI()
        micButton = findViewById<View>(R.id.mic_icon)
        micButton.setOnTouchListener(View.OnTouchListener(
                { _, event ->

                    if (!isRecordAudioPermissionGranted) {
                        return@OnTouchListener false
                    }

                    when (event.action) {

                        MotionEvent.ACTION_DOWN -> onMicPressed()

                        MotionEvent.ACTION_UP,
                        MotionEvent.ACTION_CANCEL -> onMicReleased()

                        else ->
                            return@OnTouchListener false

                    }

                    return@OnTouchListener true
                }
        ))
    }

    private fun onMicPressed() {
        service.startListening()

        micButton.animate()
                .setDuration(300)
                .scaleX(1.2.toFloat())
                .scaleY(1.2.toFloat())
                .start()
    }

    private fun onMicReleased() {
        service.stopListening()

        micButton.animate()
                .setDuration(200)
                .scaleX(1.toFloat())
                .scaleY(1.toFloat())
                .start()
    }

    private fun checkPermission() {
        isRecordAudioPermissionGranted =
                checkSelfPermission(permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

        if (!isRecordAudioPermissionGranted) {
            requestPermissions(arrayOf(permission.RECORD_AUDIO), 1)
        }
    }

    private fun initAI() {
        val config = AIConfiguration(DIALOG_FLOW_ACCESS_TOKEN, ai.api.AIConfiguration.SupportedLanguages.English, AIConfiguration.RecognitionEngine.System)

        service = AIService.getService(this, config)
        service.setListener(object : AIListener {
            override fun onListeningStarted() {
            }

            override fun onAudioLevel(level: Float) {
            }

            override fun onListeningCanceled() {
            }

            override fun onListeningFinished() {
            }

            override fun onResult(result: AIResponse?) {
                result?.let {
                    textViewDebug.text = result.result.fulfillment.speech

                    if (it.isError) {
                        return
                    }

                    for (context in it.result.contexts) {
                        if (context.name == "search-lyrics" && result.result.fulfillment.displayText!= null) {
                            showLyrics(result.result.fulfillment.displayText)
                            break
                        }
                    }
                }
            }

            override fun onError(error: AIError?) {
                error?.let {
                    textViewDebug.text = error.toString()
                }
            }

        })
    }

    private fun showLyrics(lyrics: String) {
        LyricsFragment.getInstance(lyrics).let {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container,it)
                    .addToBackStack(LyricsFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissions.forEachIndexed { index, permission ->
            if (permission == Manifest.permission.RECORD_AUDIO && grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                isRecordAudioPermissionGranted = true
            }
        }

    }


}
