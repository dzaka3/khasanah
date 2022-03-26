package com.khasanah.features.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.khasanah.R
import com.khasanah.features.dto.LoginDto
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.features.utils.Constant
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.dsl.ATTRIBUTE_VIEW_MODEL
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel : KhasanahViewModel by viewModel()
    private val prefs by inject<SharedPreferences>()
    private val model = LoginDto()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading_animation.visibility = View.INVISIBLE
        observerLogin()
        phone_et.addTextChangedListener {
            validateButton()
        }
        password_et.addTextChangedListener {
            validateButton()
        }
        button_login.setOnClickListener {
            sendData()
        }
        button_register.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    private fun validateButton(){
        if (!phone_et?.text.isNullOrEmpty() && !password_et?.text.isNullOrEmpty()){
            button_login.isEnabled = true
            button_login.setTextColor(resources.getColor(R.color.white))
            button_login.setBackgroundResource(R.drawable.btn_active)
        }else{
            button_login.isEnabled = false
            button_login.setTextColor(resources.getColor(R.color.white))
            button_login.setBackgroundResource(R.drawable.btn_noactive)
        }
    }

    private fun sendData(){
        showLoading()
        model.phone = phone_et.text.toString()
        model.password = password_et.text.toString()
        viewModel.postLogin(model)
    }

    private fun showLoading(){
        loading_animation.visibility = View.VISIBLE
        button_login.isEnabled = false
        phone_et.isEnabled = false
        password_et.isEnabled = false
    }

    private fun hideLoading(){
        loading_animation.visibility = View.INVISIBLE
        button_login.isEnabled = true
        phone_et.isEnabled = true
        password_et.isEnabled = true
    }

    private fun observerLogin() {
        viewModel.login.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        val editor = prefs.edit()
                        it.data.let {
                            editor.putString(Constant.KEY_TOKEN, it?.data?.token)
                            editor.apply()
                        }
                        findNavController().navigate(R.id.menuFragment)
                        it.status = Status.DONE
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                        hideLoading()
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }
}