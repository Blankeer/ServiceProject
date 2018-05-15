package com.blanke.serviceproject

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class TestService : Service() {

    class MyBinder : Binder() {

    }

    override fun onCreate() {
        super.onCreate()
        Log.d("service", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("service", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("service", "onBind")
        return MyBinder()
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d("service", "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("service", "onUnbind")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("service", "onDestroy")
    }
}
