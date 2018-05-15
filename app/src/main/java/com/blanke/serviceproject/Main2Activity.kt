package com.blanke.serviceproject

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.blanke.serviceproject.remote.messenger.RemoteService
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class Main2Activity : AppCompatActivity() {
    private var connected = false
    private var remoteMessenger: Messenger? = null
    private var clientMessenger: Messenger? = null

    @SuppressLint("HandlerLeak")
    private val clientHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                2 -> {
                    val bundle = msg.obj as Bundle
                    val replyText = bundle.getString("msg")
                    Toast.makeText(this@Main2Activity, replyText, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            connected = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            connected = true
            remoteMessenger = Messenger(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        clientMessenger = Messenger(clientHandler)
        bu_messenger.setOnClickListener {
            bindService(Intent(this, RemoteService::class.java), conn, Service.BIND_AUTO_CREATE)
        }
        bu_send_msg.setOnClickListener {
            if (connected) {
                val msg = Message.obtain()
                msg.what = 1
                msg.obj = Bundle().apply {
                    putString("msg", "text${Random().nextInt(100)}")
                }
                msg.replyTo = clientMessenger
                remoteMessenger?.send(msg)
            }
        }
        bu_unbind_remote.setOnClickListener {
            unBindRemoteService()
        }
    }

    private fun unBindRemoteService() {
        if (connected) {
            unbindService(conn)
            connected = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unBindRemoteService()
    }
}
