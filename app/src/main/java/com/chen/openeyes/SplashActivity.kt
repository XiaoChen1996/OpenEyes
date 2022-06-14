package com.chen.openeyes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 启动页
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this,MainActivity::class.java))
    }
}