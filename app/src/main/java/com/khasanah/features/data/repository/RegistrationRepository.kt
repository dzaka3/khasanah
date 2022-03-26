package com.khasanah.features.data.repository

import com.khasanah.features.dto.*
import com.khasanah.features.dto.ResponseRegisterDto
import retrofit2.Response
import retrofit2.http.Body

interface RegistrationRepository {

    suspend fun postRegister(@Body body: RegisterDto) : Response<ResponseRegisterDto>
    suspend fun postLogin(@Body body: LoginDto) : Response<ResponseLoginDto>
    suspend fun postDetail() : Response<ResponseDetailUserDto>
    suspend fun postUpdateDetail(@Body body: UpdateProfileDto) : Response<ResponseStatusDto>
    suspend fun getChild() : Response<ResponseChildDto>
    suspend fun postAddChild(@Body body:AddChildDto) : Response<ResponseStatusDto>
    suspend fun getChildDetail(id: String) : Response<ResponseChildDetailDto>
    suspend fun postAddChild(id: String, @Body body: AddChildDto) : Response<ResponseStatusDto>
    suspend fun postWeight(@Body body: AddChildWeightDto) : Response<ResponseStatusDto>
    suspend fun deleteWeight(id : String) : Response<ResponseChildDto>
    suspend fun getPump() : Response<ResponsePumpDto>
    suspend fun getPumpToday() : Response<ResponsePumpTodayDto>
    suspend fun getPumpStatistic() : Response<ResponsePumpStatistic>
    suspend fun postPump(@Body body: PumpDto) : Response<ResponseStatusDto>
    suspend fun deletePump(id : String) : Response<ResponseChildDto>
    suspend fun getTheory() : Response<TheoryDto>
    suspend fun getHypno() : Response<ResponseHypnoDto>
    suspend fun getFaq() : Response<ResponseHypnoDto>

}