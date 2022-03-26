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
import com.khasanah.features.utils.Constant
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.dto.RegisterDto
import com.khasanah.features.viewModel.KhasanahViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.loading_animation
import kotlinx.android.synthetic.main.fragment_register.password_et
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val prefs by inject<SharedPreferences>()
    private val viewModel : KhasanahViewModel by viewModel()
    var model= RegisterDto()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerRegister()
        name_et.addTextChangedListener {
            validateButton()
        }
        phone_number_et.addTextChangedListener {
            validateButton()
        }
        confirm_pass_et.addTextChangedListener {
            validateButton()
        }
        email_et.addTextChangedListener {
            validateButton()
        }
        password_et.addTextChangedListener {
            validateButton()
        }
        button_ok.setOnClickListener {
            sendData()
        }
    }

    private fun validateButton(){
        if(!name_et.text.toString().isNullOrBlank() && !phone_number_et.text.toString().isNullOrBlank() &&
                !password_et.text.toString().isNullOrBlank() && !confirm_pass_et.text.toString().isNullOrBlank()){
            button_ok.isEnabled = true
            button_ok.setTextColor(resources.getColor(R.color.white))
            button_ok.setBackgroundResource(R.drawable.btn_active)
        }else{
            button_ok.isEnabled = false
            button_ok.setTextColor(resources.getColor(R.color.white))
            button_ok.setBackgroundResource(R.drawable.btn_noactive)
        }
    }

    private fun sendData(){
        showLoading()
        model.name = name_et.text.toString()
        model.phone = phone_number_et.text.toString()
        model.cPassword = confirm_pass_et.text.toString()
        model.password = password_et.text.toString()
        model.email = email_et.text.toString()
        viewModel.postRegister(model)
    }


    private fun showLoading(){
        loading_animation.visibility = View.VISIBLE
        button_ok.isEnabled = false
        name_et.isEnabled = false
        phone_number_et.isEnabled = false
        password_et.isEnabled = false
        confirm_pass_et.isEnabled = false
    }

    private fun hideLoading(){
        loading_animation.visibility = View.GONE
        button_ok.isEnabled = true
        name_et.isEnabled = true
        phone_number_et.isEnabled = true
        password_et.isEnabled = true
        confirm_pass_et.isEnabled = true
    }

    private fun observerRegister() {
        viewModel.register.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        val editor = prefs.edit()
                        it.data.let {
                            editor.putString(Constant.KEY_TOKEN, it?.data?.token)
                            editor.apply()
                        }
                        it.status = Status.DONE
                        findNavController().navigate(R.id.loginFragment)
                        hideLoading()
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