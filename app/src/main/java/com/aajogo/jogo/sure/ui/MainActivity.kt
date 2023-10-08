package com.aajogo.jogo.sure.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aajogo.jogo.sure.databinding.ActivityMainBinding
import com.aajogo.jogo.sure.ui.game.MusicPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

    override fun onDestroy() {
        MusicPlayer.musicPlayer?.stop()
        MusicPlayer.musicPlayer = null
        MusicPlayer.soundPlayer?.stop()
        MusicPlayer.soundPlayer = null
        super.onDestroy()
    }
}