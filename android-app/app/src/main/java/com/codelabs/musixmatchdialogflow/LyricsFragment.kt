package com.codelabs.musixmatchdialogflow

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LyricsFragment : Fragment() {

    companion object {
        fun getInstance(lyrics: String): LyricsFragment {
            val args = Bundle()
            args.putString("lyrics", lyrics)

            LyricsFragment().let {
                it.arguments = args
                return it
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lyrics_fragment_layout, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lyrics = arguments.getString("lyrics")

        view.findViewById<TextView>(R.id.lyrics_text).text = lyrics

    }

}