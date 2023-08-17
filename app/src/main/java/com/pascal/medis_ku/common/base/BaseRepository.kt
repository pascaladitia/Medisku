package com.pascal.medis_ku.common.base

import com.pascal.medis_ku.common.wrapper.Resource

abstract class BaseRepository {
    suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}