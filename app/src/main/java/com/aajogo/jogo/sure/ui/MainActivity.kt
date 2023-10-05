package com.aajogo.jogo.sure.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aajogo.jogo.sure.R
import com.aajogo.jogo.sure.ui.game.MusicPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        MusicPlayer.musicPlayer?.stop()
        MusicPlayer.musicPlayer = null
        MusicPlayer.soundPlayer?.stop()
        MusicPlayer.soundPlayer = null
        super.onDestroy()
    }
}