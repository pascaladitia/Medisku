package com.pascal.medis_ku.data.repository

import com.pascal.medis_ku.common.base.BaseRepository
import com.pascal.medis_ku.common.wrapper.Resource
import com.pascal.medis_ku.data.datasource.NetworkDatasource
import com.pascal.medis_ku.data.network.model.ResponseLogin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

interface NetworkRepository {
    suspend fun login(body: RequestBody): Flow<Resource<ResponseLogin>>
}

class NetworkRepositoryImpl @Inject constructor(
    private val networkDatasource: NetworkDatasource
): NetworkRepository, BaseRepository() {
    override suspend fun login(body: RequestBody): Flow<Resource<ResponseLogin>> = flow {
        emit(proceed { networkDatasource.login(body)})
    }
}