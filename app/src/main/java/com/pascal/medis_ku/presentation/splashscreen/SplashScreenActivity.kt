package com.pascal.medis_ku.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pascal.medis_ku.R
import com.pascal.medis_ku.common.utils.PrefLogin
import com.pascal.medis_ku.presentation.login.LoginActivity
import com.pascal.medis_ku.presentation.main.HomeActivity
import com.pascal.medis_ku.presentation.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var prefLogin: PrefLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        runBlocking {
            lifecycleScope.launch {
                delay(2000)
                actionIntent()
                finish()
            }
        }
    }

    private fun actionIntent() {
        prefLogin = PrefLogin()
        prefLogin.initPref(this)

        if (prefLogin.rememberMe() == true) {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
        }
    }
}