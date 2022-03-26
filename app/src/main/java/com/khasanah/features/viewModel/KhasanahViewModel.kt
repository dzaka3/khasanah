package com.khasanah.features.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khasanah.features.utils.NetworkHelper
import com.khasanah.features.data.repository.impl.RegistrationRepositoryImpl
import com.khasanah.features.di.moshi
import com.khasanah.features.dto.*
import com.khasanah.features.utils.Resource
import com.khasanah.features.utils.ResourcesHelper
import kotlinx.coroutines.launch
import org.koin.android.BuildConfig
import org.koin.core.KoinComponent
import org.koin.core.inject

class KhasanahViewModel : ViewModel(), KoinComponent {
    private val networkHelper by inject<NetworkHelper>()
    private val registrationRepository by inject<RegistrationRepositoryImpl>()
    private val resourceHelper by inject<ResourcesHelper>()

    private val _login  = MutableLiveData <Resource<ResponseLoginDto>>()
    val login: LiveData<Resource<ResponseLoginDto>>
        get() = _login

    private val _register  = MutableLiveData <Resource<ResponseRegisterDto>>()
    val register: LiveData<Resource<ResponseRegisterDto>>
        get() = _register

    private val _detailUser  = MutableLiveData <Resource<ResponseDetailUserDto>>()
    val detailUser: LiveData<Resource<ResponseDetailUserDto>>
        get() = _detailUser

    private val _updateDetailUser  = MutableLiveData <Resource<ResponseStatusDto>>()
    val updateDetailUser: LiveData<Resource<ResponseStatusDto>>
        get() = _updateDetailUser

    private val _listAnak  = MutableLiveData <Resource<ResponseChildDto>>()
    val listAnak: LiveData<Resource<ResponseChildDto>>
        get() = _listAnak

    private val _addChild  = MutableLiveData <Resource<ResponseStatusDto>>()
    val addChild: LiveData<Resource<ResponseStatusDto>>
        get() = _addChild

    private val _getDetailChild  = MutableLiveData <Resource<ResponseChildDetailDto>>()
    val getDetailChild: LiveData<Resource<ResponseChildDetailDto>>
        get() = _getDetailChild

    private val _updateDetailAnak  = MutableLiveData <Resource<ResponseStatusDto>>()
    val updateDetailAnak: LiveData<Resource<ResponseStatusDto>>
        get() = _updateDetailAnak

    private val _getTheory  = MutableLiveData <Resource<TheoryDto>>()
    val getTheory: LiveData<Resource<TheoryDto>>
        get() = _getTheory

    private val _getHypno  = MutableLiveData <Resource<ResponseHypnoDto>>()
    val getHypno: LiveData<Resource<ResponseHypnoDto>>
        get() = _getHypno

    private val _getPump  = MutableLiveData <Resource<ResponsePumpDto>>()
    val getPump: LiveData<Resource<ResponsePumpDto>>
        get() = _getPump

    private val _postPump  = MutableLiveData <Resource<ResponseStatusDto>>()
    val postPump: LiveData<Resource<ResponseStatusDto>>
        get() = _postPump

    private val _getPumpStatistic  = MutableLiveData <Resource<ResponsePumpStatistic>>()
    val getPumpStatistic: LiveData<Resource<ResponsePumpStatistic>>
        get() = _getPumpStatistic

    private val _getPumpToday  = MutableLiveData <Resource<ResponsePumpTodayDto>>()
    val getPumpToday: LiveData<Resource<ResponsePumpTodayDto>>
        get() = _getPumpToday

    private val _postAddWeight  = MutableLiveData <Resource<ResponseStatusDto>>()
    val postAddWeight: LiveData<Resource<ResponseStatusDto>>
        get() = _postAddWeight

    fun postLogin(loginDto : LoginDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _login.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postLogin(loginDto).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _login.postValue(Resource.success(it.body()!!))
                            }else{
                                _login.postValue(Resource.error(it.body()!!.message!!, null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _login.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _login.postValue(Resource.error(errorBody.toString(), null))
                            }else _login.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "postLogin: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _login.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _login.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun postRegister(registerDto : RegisterDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _register.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postRegister(registerDto).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _register.postValue(Resource.success(it.body()!!))
                            }else{
                                _register.postValue(Resource.error(it.body()!!.message!!, null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _register.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _register.postValue(Resource.error(errorBody.toString(), null))
                            }else _register.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "postRegister: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _register.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _register.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun postDetailUser(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _detailUser.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postDetail().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _detailUser.postValue(Resource.success(it.body()!!))
                            }else{
                                _detailUser.postValue(Resource.error(it.body()!!.message!!, null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _detailUser.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _detailUser.postValue(Resource.error(errorBody.toString(), null))
                            }else _detailUser.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "postDetailUser: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _detailUser.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _detailUser.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun postUpdateDetail(model : UpdateProfileDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _updateDetailUser.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postUpdateDetail(model).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _updateDetailUser.postValue(Resource.success(it.body()!!))
                            }else{
                                _updateDetailUser.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _updateDetailUser.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _updateDetailUser.postValue(Resource.error(errorBody.toString(), null))
                            }else _updateDetailUser.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "postUpdateDetail: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _updateDetailUser.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _updateDetailUser.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getChild(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _listAnak.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getChild().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _listAnak.postValue(Resource.success(it.body()!!))
                            }else{
                                _listAnak.postValue(Resource.error(it.body()!!.message!!, null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _listAnak.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _listAnak.postValue(Resource.error(errorBody.toString(), null))
                            }else _listAnak.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getChild: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _listAnak.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _listAnak.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun postAddChild(body : AddChildDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _addChild.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postAddChild(body).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _addChild.postValue(Resource.success(it.body()!!))
                            }else{
                                _addChild.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _addChild.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _addChild.postValue(Resource.error(errorBody.toString(), null))
                            }else _addChild.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getChild: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _addChild.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _addChild.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getChildDetail(id : String){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getDetailChild.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getChildDetail(id).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getDetailChild.postValue(Resource.success(it.body()!!))
                            }else{
                                _getDetailChild.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getDetailChild.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getDetailChild.postValue(Resource.error(errorBody.toString(), null))
                            }else _getDetailChild.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getChild: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getDetailChild.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getDetailChild.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun updateDataChild(id : String, model : AddChildDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _updateDetailAnak.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postAddChild(id, model).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _updateDetailAnak.postValue(Resource.success(it.body()!!))
                            }else{
                                _updateDetailAnak.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _updateDetailAnak.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _updateDetailAnak.postValue(Resource.error(errorBody.toString(), null))
                            }else _updateDetailAnak.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "updateDataChild: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _updateDetailAnak.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _updateDetailAnak.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getTheory(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getTheory.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getTheory().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getTheory.postValue(Resource.success(it.body()!!))
                            }else{
                                _getTheory.postValue(Resource.error(it.body()!!.message!!, null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getTheory.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getTheory.postValue(Resource.error(errorBody.toString(), null))
                            }else _getTheory.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getTheory.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getTheory.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getHypno(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getHypno.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getHypno().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getHypno.postValue(Resource.success(it.body()!!))
                            }else{
                                _getHypno.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getHypno.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getHypno.postValue(Resource.error(errorBody.toString(), null))
                            }else _getHypno.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getHypno.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getHypno.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getFaq(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getHypno.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getFaq().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getHypno.postValue(Resource.success(it.body()!!))
                            }else{
                                _getHypno.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getHypno.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getHypno.postValue(Resource.error(errorBody.toString(), null))
                            }else _getHypno.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getHypno.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getHypno.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getPump(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getPump.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getPump().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getPump.postValue(Resource.success(it.body()!!))
                            }else{
                                _getPump.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getPump.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getPump.postValue(Resource.error(errorBody.toString(), null))
                            }else _getPump.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getPump.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getPump.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun postPump(model : PumpDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _postPump.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postPump(model).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _postPump.postValue(Resource.success(it.body()!!))
                            }else{
                                _postPump.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _postPump.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _postPump.postValue(Resource.error(errorBody.toString(), null))
                            }else _postPump.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _postPump.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _postPump.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getPumpStatistic(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getPumpStatistic.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getPumpStatistic().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getPumpStatistic.postValue(Resource.success(it.body()!!))
                            }else{
                                _getPumpStatistic.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getPumpStatistic.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getPumpStatistic.postValue(Resource.error(errorBody.toString(), null))
                            }else _getPumpStatistic.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getPumpStatistic.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getPumpStatistic.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getPumpToday(){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _getPumpToday.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.getPumpToday().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _getPumpToday.postValue(Resource.success(it.body()!!))
                            }else{
                                _getPumpToday.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _getPumpToday.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _getPumpToday.postValue(Resource.error(errorBody.toString(), null))
                            }else _getPumpToday.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _getPumpToday.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _getPumpToday.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun postWeight(body : AddChildWeightDto){
        viewModelScope.launch {
            //loading
            //jika terkoneksi internet
            _postAddWeight.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                try {
                    registrationRepository.postWeight(body).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            if (it.body()?.status != "Error"){
                                _postAddWeight.postValue(Resource.success(it.body()!!))
                            }else{
                                _postAddWeight.postValue(Resource.error(it.body()!!.message!!.toString(), null))
                            }
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                val statusJson = moshi.adapter(ResponseStatusDto::class.java).fromJson(errorBody)
                                if (!statusJson?.message.toString().isNullOrEmpty()){
                                    _postAddWeight.postValue(Resource.error(statusJson!!.message!!.toString(), null))
                                }else _postAddWeight.postValue(Resource.error(errorBody.toString(), null))
                            }else _postAddWeight.postValue(Resource.error(it.code().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Exception", "getTheory: " + e)
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _postAddWeight.value = Resource.error(resourceHelper.errorSystem, null)
                }
            }else{
                _postAddWeight.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

}