package com.khasanah.features.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.khasanah.features.adapter.ListNamaAnakAdapter
import com.khasanah.R
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.dto.AddChildDto
import com.khasanah.features.dto.ResponseChildDto
import com.khasanah.features.viewModel.KhasanahViewModel
import kotlinx.android.synthetic.main.dialog_common.*
import kotlinx.android.synthetic.main.fragment_data_anak.*
import kotlinx.android.synthetic.main.fragment_data_anak.btn_back
import kotlinx.android.synthetic.main.fragment_data_anak.list_title_rv
import kotlinx.android.synthetic.main.fragment_data_anak.view.*
import kotlinx.android.synthetic.main.fragment_detail_child_data.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class DataAnakFragment : Fragment() {

    var selectedGender = ""
    var addChildDto = AddChildDto()
    private val viewModel : KhasanahViewModel by viewModel()
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
        return inflater.inflate(R.layout.fragment_data_anak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.swipe_refresh
        viewModel.getChild()
        observerData()
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
        btn_tambah_data.setOnClickListener {
            findNavController().navigate(R.id.menuAddDataAnak)
        }

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getChild()
                shimmer_data_anak.isVisible = true
            }, 2000)
        }
    }

    private fun observerData() {
        viewModel.listAnak.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            list_title_rv.layoutManager = LinearLayoutManager(this.activity)
                            val listTitle = mutableListOf<ResponseChildDto.Data>()
                            listTitle.clear()
                            it!!.listIterator().forEach {
                                listTitle.add(it)
                            }
                            val adapter = ListNamaAnakAdapter(requireContext(), listTitle)
                            adapter.notifyDataSetChanged()
                            list_title_rv.adapter = adapter
                        }

                        shimmer_data_anak.isVisible = false
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

}