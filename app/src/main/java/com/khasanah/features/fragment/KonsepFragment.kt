package com.khasanah.features.fragment

import android.content.SharedPreferences
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
import com.khasanah.features.adapter.ListKonsepContentAdapter
import com.khasanah.R
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.dto.TheoryDto
import com.khasanah.features.viewModel.KhasanahViewModel
import kotlinx.android.synthetic.main.fragment_konsep.*
import kotlinx.android.synthetic.main.fragment_konsep.btn_back
import kotlinx.android.synthetic.main.fragment_konsep.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class KonsepFragment : Fragment() {

    lateinit var swipeRefresh: SwipeRefreshLayout
    private val prefs by inject<SharedPreferences>()
    private val viewModel : KhasanahViewModel by viewModel()
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
        return inflater.inflate(R.layout.fragment_konsep, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmer_konsep.isVisible = true
        swipeRefresh = view.swipe_refresh
        viewModel.getTheory()
        observerData()
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getTheory()
                shimmer_konsep.isVisible = true
            }, 2000)
        }
    }

    private fun observerData() {
        viewModel.getTheory.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            list_title_rv.layoutManager = LinearLayoutManager(this.activity)
                            val listTitle = mutableListOf<TheoryDto.Data>()
                            listTitle.clear()
                            it!!.listIterator().forEach {
                                listTitle.add(it)
                            }
                            val adapter = ListKonsepContentAdapter(requireContext(), listTitle)
                            adapter.notifyDataSetChanged()
                            list_title_rv.adapter = adapter
                        }
                        shimmer_konsep.isVisible = false
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