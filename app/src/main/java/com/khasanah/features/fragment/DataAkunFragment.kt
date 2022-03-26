package com.khasanah.features.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.khasanah.R
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.dto.UpdateProfileDto
import com.khasanah.features.viewModel.KhasanahViewModel
import kotlinx.android.synthetic.main.dialog_common.*
import kotlinx.android.synthetic.main.fragment_data_akun.*
import kotlinx.android.synthetic.main.fragment_data_akun.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class DataAkunFragment : Fragment() {

    private val viewModel : KhasanahViewModel by viewModel()
    var updateProfileDto = UpdateProfileDto()
    lateinit var swipeRefresh: SwipeRefreshLayout

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
        return inflater.inflate(R.layout.fragment_data_akun, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.swipe_refresh
        viewModel.postDetailUser()
        observerData()
        observerUpdateData()
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
        btn_save.setOnClickListener {
            sendData()
        }

        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.postDetailUser()
                showLoading()
            }, 2000)
        }
    }

    private fun sendData(){
        updateProfileDto.name = et_personal_nama.text.toString()
        updateProfileDto.phone = et_personal_telepon.text.toString()
        updateProfileDto.email = et_personal_email.text.toString()
        updateProfileDto.address = et_personal_tempat.text.toString()
        updateProfileDto.age = et_personal_tanggal.text.toString()

        viewModel.postUpdateDetail(updateProfileDto)
    }

    private fun observerUpdateData() {
        viewModel.updateDetailUser.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            dialog(it.status.toString())
                        }
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

    private fun observerData() {
        viewModel.detailUser.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            et_personal_nama.setText(it?.name ?: "")
                            et_personal_telepon.setText(it?.phone ?: "")
                            et_personal_email.setText(it?.email ?: "")
                            et_personal_tempat.setText(it?.address ?: "")
                            et_personal_tanggal.setText(it?.age ?: "")
                        }
                        hideLoading()
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

    fun dialog(message: String) {
        val customDialog = Dialog(this.requireActivity())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_common, null)
        customDialog.setContentView(dialogLayout)
//        customDialog.imageView.setImageResource(R.drawable.ic_checklist_white_blue)
        customDialog.lbl_title.text = message

        customDialog.lbl_desc.visibility = View.GONE
        customDialog.lbl_desc2.visibility = View.GONE
        customDialog.btn_okay.text = "OK"
        customDialog.btn_okay.setOnClickListener {
            if (customDialog.isShowing)
                customDialog.dismiss()
        }
        customDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        customDialog.show()
    }

    private fun hideLoading(){
        et_personal_nama.visibility = View.VISIBLE
        et_personal_telepon.visibility = View.VISIBLE
        et_personal_email.visibility = View.VISIBLE
        et_personal_tempat.visibility = View.VISIBLE
        et_personal_tanggal.visibility = View.VISIBLE
        shimmer_nama.visibility = View.GONE
        shimmer_telepon.visibility = View.GONE
        shimmer_email.visibility = View.GONE
        shimmer_tempat.visibility = View.GONE
        shimmer_tanggal.visibility = View.GONE
    }

    private fun showLoading(){
        et_personal_nama.visibility = View.GONE
        et_personal_telepon.visibility = View.GONE
        et_personal_email.visibility = View.GONE
        et_personal_tempat.visibility = View.GONE
        et_personal_tanggal.visibility = View.GONE
        shimmer_nama.visibility = View.VISIBLE
        shimmer_telepon.visibility = View.VISIBLE
        shimmer_email.visibility = View.VISIBLE
        shimmer_tempat.visibility = View.VISIBLE
        shimmer_tanggal.visibility = View.VISIBLE
    }
}