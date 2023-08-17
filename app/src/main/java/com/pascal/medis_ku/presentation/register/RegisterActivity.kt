package com.pascal.medis_ku.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pascal.medis_ku.R
import com.pascal.medis_ku.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            btnLogin.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            btnRegister.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}