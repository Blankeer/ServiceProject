package com.blanke.serviceproject.remote.messenger

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

class RemoteService : Service() {

    private lateinit var myMessenger: Messenger

    @SuppressLint("HandlerLeak")
    private val myHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                1 -> {
                    val bundle = msg.obj as Bundle
                    val text = bundle.getString("msg")
                    Log.d("RemoteService", "收到客户端消息：$text")
                    val replyMsg = Message.obtain()
                    replyMsg.what = 2
                    replyMsg.obj = Bundle().apply {
                        putString("msg", "reply: $text")
                    }
                    Log.d("RemoteService", "回复客户端消息")
                    msg.replyTo.send(replyMsg)
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        myMessenger = Messenger(myHandler)
        Log.d("RemoteService", "onBind")
        return myMessenger.binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("RemoteService", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("RemoteService", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d("RemoteService", "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("RemoteService", "onUnbind")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("RemoteService", "onDestroy")
    }
}
