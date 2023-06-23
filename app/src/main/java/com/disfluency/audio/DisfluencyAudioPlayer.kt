package com.disfluency.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class DisfluencyAudioPlayer(private val context: Context) {

    private var player: MediaPlayer? = null

    private var position: MutableState<Int> = mutableStateOf(0)
    private var totalDuration: MutableState<Int> = mutableStateOf(0)
    private var isPlaying: MutableState<Boolean> = mutableStateOf(false)
    private var asyncReady: MutableState<Boolean> = mutableStateOf(false)

    private var progressTrackerJob: Job? = null

    fun play(file: File){
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
            evaluateProgress()
        }
    }

    fun loadUrl(audioUrl: String){
        MediaPlayer().apply {
            player = this

            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            try {
                setDataSource(context, Uri.parse(audioUrl))
                prepareAsync()
                setOnPreparedListener {
                    asyncReady.value = true
                    totalDuration.value = duration
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun play(){
        println("Play!")
        isPlaying.value = true

        player?.start()
        evaluateProgress()
    }

    fun pause(){
        println("Pause!")
        isPlaying.value = false
        player?.pause()
        progressTrackerJob?.cancel()
    }

    fun stop(){
        println("Stop!")
        isPlaying.value = false
        player?.stop()
        progressTrackerJob?.cancel()
        position.value = 0

        //TODO: el release habria que hacerlo cuando salimos de la pantalla! tambien cancelar el job
//        player?.release()
//        player = null
    }

    //TODO: cuando lo arrastro hasta el final termina pero no lo puedo volver a arrancar,
    fun seekTo(millis: Int){
        println("Seek to: " + millis)

        if (millis >= totalDuration.value)
            stop()
        else {
            player?.seekTo(millis)
            position.value = millis
        }
    }

    private fun evaluateProgress(){
        progressTrackerJob?.cancel()
        progressTrackerJob = CoroutineScope(Dispatchers.Default).launch {

            while (isPlaying()){
//                println(totalDuration.value)
//                println(position.value)

                position.value = player!!.currentPosition

                if (position.value >= totalDuration.value) stop()
            }
        }
    }

    fun position(): Int {
        return position.value
    }

    fun duration(): Int {
        return totalDuration.value
    }

    fun isPlaying(): Boolean {
        return isPlaying.value
    }

    fun asyncReady(): Boolean {
        return asyncReady.value
    }


}