package com.vaccine.poller

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_splash)
        bt_continue.setOnClickListener {
            if (et_area_code.text.isNullOrEmpty().not() && et_auth_token.text.isNullOrEmpty()) {
                updateSharePref()
                MainActivity.areaCode = et_area_code.text.toString().toInt()
                RequestInterceptor.myAuthToken = et_auth_token.text.toString()
                startMainActivity()

            } else {
                Toast.makeText(this.applicationContext, "Fields are empty", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun updateSharePref() {

    }

    private fun startMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity( intent)
        finish()
    }
}