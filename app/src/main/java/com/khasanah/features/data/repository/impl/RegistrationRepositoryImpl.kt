package com.khasanah.features.data.repository.impl

import android.content.Context
import com.khasanah.features.data.api.ApiService
import com.khasanah.features.data.repository.RegistrationRepository
import com.khasanah.features.dto.*
import com.khasanah.features.dto.ResponseRegisterDto
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.KoinComponent
import retrofit2.Response

//https://stackoverflow.com/questions/49629377/koin-how-to-inject-outside-of-android-activity-appcompatactivity
class RegistrationRepositoryImpl(
        private val context: Context,
        private val backgroundDispatcher: CoroutineDispatcher,
        private val apiService: ApiService //constructor injection
) : RegistrationRepository, KoinComponent {


    override suspend fun postRegister(body: RegisterDto): Response<ResponseRegisterDto> {
        return apiService.postRegister(body)
    }

    override suspend fun postLogin(body: LoginDto): Response<ResponseLoginDto> {
        return apiService.postLogin(body)
    }

    override suspend fun postDetail(): Response<ResponseDetailUserDto> {
        return apiService.postDetail()
    }

    override suspend fun postUpdateDetail(body: UpdateProfileDto): Response<ResponseStatusDto> {
        return apiService.postUpdate(body)
    }

    override suspend fun getChild(): Response<ResponseChildDto> {
        return apiService.getChild()
    }

    override suspend fun postAddChild(body: AddChildDto): Response<ResponseStatusDto> {
        return apiService.postAddChild(body)
    }

    override suspend fun getChildDetail(id: String): Response<ResponseChildDetailDto> {
        return apiService.getChildDetail(id)
    }

    override suspend fun postAddChild(id: String, body: AddChildDto): Response<ResponseStatusDto> {
        return apiService.postAddChild(id, body)
    }

    override suspend fun postWeight(body: AddChildWeightDto): Response<ResponseStatusDto> {
        return apiService.postWeight(body)
    }

    override suspend fun deleteWeight(id: String): Response<ResponseChildDto> {
        return apiService.deleteWeight(id)
    }

    override suspend fun getPump(): Response<ResponsePumpDto> {
        return apiService.getPump()
    }

    override suspend fun getPumpToday(): Response<ResponsePumpTodayDto> {
        return apiService.getPumpToday()
    }

    override suspend fun getPumpStatistic(): Response<ResponsePumpStatistic> {
        return apiService.getPumpStatistic()
    }

    override suspend fun postPump(body: PumpDto): Response<ResponseStatusDto> {
        return apiService.postPump(body)
    }

    override suspend fun deletePump(id: String): Response<ResponseChildDto> {
        return apiService.deletePump(id)
    }

    override suspend fun getTheory(): Response<TheoryDto> {
        return apiService.getTheory()
    }

    override suspend fun getHypno(): Response<ResponseHypnoDto> {
        return apiService.getHypno()
    }

    override suspend fun getFaq(): Response<ResponseHypnoDto> {
        return apiService.getFaq()
    }

}