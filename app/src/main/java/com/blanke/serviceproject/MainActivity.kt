package com.blanke.serviceproject

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var myBinder: TestService.MyBinder? = null
    private var mBound = false

    private val conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("service", "onServiceDisconnected name=$name")
            myBinder = null
            mBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("service", "onServiceConnected name=$name")
            myBinder = service as TestService.MyBinder?
            mBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bu_start.setOnClickListener {
            startService(Intent(this, TestService::class.java))
        }
        bu_bind.setOnClickListener {
            bindService(Intent(this, TestService::class.java), conn, Service.BIND_AUTO_CREATE)
        }
        bu_stop.setOnClickListener {
            stopService(Intent(this, TestService::class.java))
        }
        bu_unbind.setOnClickListener {
            unBindTestService()
        }
        bu_next_page.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        bu_remote.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
        }
    }

    private fun unBindTestService() {
        if (mBound) {
            unbindService(conn)
            mBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unBindTestService()
    }
}
