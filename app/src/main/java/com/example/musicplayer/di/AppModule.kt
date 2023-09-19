package com.example.musicplayer.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppModule : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}