package com.pascal.medis_ku.presentation.login

import com.pascal.medis_ku.common.wrapper.Resource
import com.pascal.medis_ku.data.network.model.ResponseLogin
import kotlinx.coroutines.flow.StateFlow
import okhttp3.RequestBody

interface LoginContract {
    val loginResult: StateFlow<Resource<ResponseLogin>>
    fun login(body: RequestBody)
}