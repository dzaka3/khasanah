package com.khasanah.features.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.khasanah.features.dto.PumpDto
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.R
import kotlinx.android.synthetic.main.fragment_pompa_asi.*
import kotlinx.android.synthetic.main.fragment_pompa_asi.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.fragment_pompa_asi.btn_save

class PompaAsiFragment : Fragment() {

    private val viewModel : KhasanahViewModel by viewModel()
    lateinit var swipeRefresh: SwipeRefreshLayout
    var modelData = PumpDto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pompa_asi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.swipe_refresh
        viewModel.getPumpToday()
        observerPumpToday()
        observerPostPump()
        btn_statistik.setOnClickListener {
            findNavController().popBackStack(R.id.menuPompaAsi, true)
            findNavController().navigate(R.id.menuStatistik)
        }

        btn_save.setOnClickListener {
            sendData()
        }

        et_left.addTextChangedListener{
            validateBtn()
        }
        et_right.addTextChangedListener{
            validateBtn()
        }

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getPumpToday()
            }, 2000)
        }

    }

    private fun validateBtn(){
        if (!et_left.text.toString().isNullOrEmpty() && !et_right.text.toString().isNullOrEmpty()){
            btn_save.isEnabled = true
            btn_save.setTextColor(resources.getColor(R.color.white))
            btn_save.setBackgroundResource(R.drawable.btn_active)
        }else {
            btn_save.isEnabled = false
            btn_save.setTextColor(resources.getColor(R.color.white))
            btn_save.setBackgroundResource(R.drawable.btn_noactive)
        }
    }

    private fun observerPumpToday() {
        viewModel.getPumpToday.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            try {
                                if (!it?.total.isNullOrEmpty()) data_total_tv.setText(it!!.total!!) else data_total_tv.setText("-")
                                if (!it?.pleft.isNullOrEmpty()) data_left_tv.setText(it!!.pleft!!) else data_left_tv.setText("-")
                                if (!it?.pright.isNullOrEmpty()) data_right_tv.setText(it!!.pright!!) else data_right_tv.setText("-")
                            } catch (e : Exception){
                                ViewUtil.dialogError(requireContext(), e.toString())
                            }
                        }
                        it.status = Status.DONE
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

    private fun observerPostPump() {
        viewModel.postPump.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data.let {
                            ViewUtil.dialogInfo(requireContext(), it?.message.toString())
                        }
                        et_left.setText("")
                        et_right.setText("")
                        it.status = Status.DONE
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                    }

                    Status.DONE -> {
                    }
                }
            })
    }

    private fun sendData(){
        modelData.left = et_left.text.toString()
        modelData.right = et_right.text.toString()
        viewModel.postPump(modelData)
    }
}