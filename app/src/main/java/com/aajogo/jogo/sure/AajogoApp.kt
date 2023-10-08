package com.aajogo.jogo.sure

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AajogoApp: Application() {
    init {
        println("This is Aajogo App")
    }
}