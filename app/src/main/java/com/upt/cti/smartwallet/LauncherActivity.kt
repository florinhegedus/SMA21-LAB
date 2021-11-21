package com.upt.cti.smartwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.upt.cti.smartwallet.model.FirebaseHelper

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        FirebaseHelper.init()
    }
}