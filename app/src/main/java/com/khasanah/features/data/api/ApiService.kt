package com.khasanah.features.data.api

import com.khasanah.features.dto.*
import com.khasanah.features.dto.ResponseRegisterDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun postRegister(@Body body: RegisterDto): Response<ResponseRegisterDto>

    @POST("login")
    suspend fun postLogin(@Body body: LoginDto): Response<ResponseLoginDto>

    @POST("details")
    suspend fun postDetail(): Response<ResponseDetailUserDto>

    @POST("update")
    suspend fun postUpdate(@Body body: UpdateProfileDto): Response<ResponseStatusDto>

    @GET("child")
    suspend fun getChild(): Response<ResponseChildDto>

    @POST("child")
    suspend fun postAddChild(@Body body: AddChildDto): Response<ResponseStatusDto>

    @GET("child/{id}")
    suspend fun getChildDetail(@Path("id") id: String): Response<ResponseChildDetailDto>

    @POST("child/{id}")
    suspend fun postAddChild(@Path("id") id: String,
                             @Body body: AddChildDto): Response<ResponseStatusDto>

    @POST("child/weight")
    suspend fun postWeight(@Body body: AddChildWeightDto): Response<ResponseStatusDto>

    @GET("weight")
    suspend fun getWeight(): Response<ResponseChildDto>

    @DELETE("child/weight/{id}")
    suspend fun deleteWeight(@Path("id") id: String): Response<ResponseChildDto>

    @GET("pump")
    suspend fun getPump(): Response<ResponsePumpDto>

    @GET("pump/statistic")
    suspend fun getPumpStatistic(): Response<ResponsePumpStatistic>

    @GET("pump/statistic/today")
    suspend fun getPumpToday(): Response<ResponsePumpTodayDto>

    @POST("pump")
    suspend fun postPump(@Body body: PumpDto): Response<ResponseStatusDto>

    @DELETE("pump/{id}")
    suspend fun deletePump(@Path("id") id: String): Response<ResponseChildDto>

    @GET("theory")
    suspend fun getTheory(): Response<TheoryDto>

    @GET("hypno")
    suspend fun getHypno(): Response<ResponseHypnoDto>

    @GET("faq")
    suspend fun getFaq(): Response<ResponseHypnoDto>

}