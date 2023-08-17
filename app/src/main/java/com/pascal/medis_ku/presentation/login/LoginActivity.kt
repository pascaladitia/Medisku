package com.pascal.medis_ku.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pascal.medis_ku.common.utils.Constant
import com.pascal.medis_ku.common.utils.PrefLogin
import com.pascal.medis_ku.common.utils.isVisibility
import com.pascal.medis_ku.common.utils.showAlert
import com.pascal.medis_ku.common.wrapper.Resource
import com.pascal.medis_ku.data.network.model.ResponseLogin
import com.pascal.medis_ku.databinding.ActivityLoginBinding
import com.pascal.medis_ku.presentation.main.HomeActivity
import com.pascal.medis_ku.presentation.main.MainActivity
import com.pascal.medis_ku.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var prefLogin: PrefLogin? = null
    private var isHidePassword = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        onView()
        onClick()
        observeData()
    }

    private fun onView() {
        prefLogin = PrefLogin()
        prefLogin?.initPref(this)
    }

    private fun onClick() {
        with(binding) {
            btnLogin.setOnClickListener {
                validation()
            }

            btnRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            etPassword.onRightDrawableClicked {
                if (isHidePassword) {
                    isHidePassword = false
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    isHidePassword = true
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        }
    }

    private fun validation() {
        with(binding) {
            if (etEmail.text.isEmpty()) {
                etEmail.error = Constant.FORM_EMPTY
            } else if (etPassword.text.isEmpty()) {
                etPassword.error = Constant.FORM_EMPTY
            } else {
                actionLogin()
            }

        }
    }

    private fun actionLogin() {
        val result = """
                  {
                    "username": "${binding.etEmail.text}",
                    "password": "${binding.etPassword.text}"
                  }
                        """
        val jsonParser = JSONObject(result)
        val requestBody = jsonParser.toString().toRequestBody("application/json".toMediaTypeOrNull())
        viewModel.login(requestBody)
    }

    private fun observeData() {
        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.loginResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            showRespone(it.data)
                            Log.d("TAG", "observeData: ${it.data}")
                        }
                        is Resource.Error -> {
                            showLoading(false)
                            showAlert(Constant.APP_NAME, it.message.toString())
                            Log.d("TAG", "observeData: ${it.exception} or ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun showRespone(data: ResponseLogin?) {
        Log.e("tag data", data.toString())
        if (!data?.token.isNullOrEmpty()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            showAlert(Constant.APP_NAME, Constant.ERROR_MESSAGE1)
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.apply {
            if (isVisible) {
                isVisibility(proggressLogin, true)
            } else {
                isVisibility(proggressLogin, false)
            }
        }
    }

    fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
        this.setOnTouchListener { v, event ->
            var hasConsumed = false
            if (v is EditText) {
                if (event.x >= v.width - v.totalPaddingRight) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        onClicked(this)
                    }
                    hasConsumed = true
                }
            }
            hasConsumed
        }
    }
}