package com.blanke.serviceproject.remote.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder

class Remote2Service : Service() {
    private val myBinder = object : IMyAidlInterface.Stub() {
        override fun sendMsg(text: String?): String {
            return "aidl reply: $text"
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }
}
