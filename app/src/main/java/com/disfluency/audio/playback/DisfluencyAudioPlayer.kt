package com.disfluency.audio.playback

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class DisfluencyAudioPlayer(private val context: Context) {

    private var player: MediaPlayer? = null

    fun play(file: File){
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    fun stop(){
        player?.stop()
        player?.release()
        player = null

    }
}