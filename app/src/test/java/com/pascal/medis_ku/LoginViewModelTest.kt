package com.pascal.medis_ku

import com.pascal.medis_ku.common.wrapper.Resource
import com.pascal.medis_ku.data.network.model.ResponseLogin
import com.pascal.medis_ku.data.repository.NetworkRepository
import com.pascal.medis_ku.presentation.login.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.spy

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mockNetworkRepository: NetworkRepository

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockNetworkRepository = mockk()
        loginViewModel = LoginViewModel(mockNetworkRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetLoginSuccess() = runBlocking {
        val result = """
              {
                "email": "eve.holt@reqres.in",
                "password": "cityslicka"
              }
                    """

        val jsonParser = spy(JSONObject(result))
        doReturn(result).`when`(jsonParser).toString()

        val requestBody =
            jsonParser.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val login = ResponseLogin()
        val expectedResource = Resource.Success(login)
        coEvery { mockNetworkRepository.login(any()) } returns flowOf(expectedResource)

        loginViewModel.login(requestBody)

        val actualResource: Resource<ResponseLogin> = loginViewModel.loginResult.value
        assertEquals(expectedResource::class, actualResource::class)
        assertEquals(expectedResource.data, actualResource.data)
    }
}