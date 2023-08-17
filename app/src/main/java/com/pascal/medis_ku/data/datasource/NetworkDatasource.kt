package com.pascal.medis_ku.data.datasource

import com.pascal.medis_ku.data.network.model.ResponseLogin
import com.pascal.medis_ku.data.network.service.NetworkService
import okhttp3.RequestBody
import javax.inject.Inject

interface NetworkDatasource {
    suspend fun login(body: RequestBody): ResponseLogin
}

class NetworkDatasourceImpl @Inject constructor(
    private val networkService: NetworkService
) : NetworkDatasource {
    override suspend fun login(body: RequestBody): ResponseLogin {
        return networkService.login(body)
    }
}