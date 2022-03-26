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
import com.khasanah.features.dto.ResponseHypnoDto
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.R
import com.khasanah.features.adapter.FaqContentAdapter
import kotlinx.android.synthetic.main.fragment_fa_q.*
import kotlinx.android.synthetic.main.fragment_fa_q.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FaQFragment : Fragment() {
    lateinit var swipeRefresh: SwipeRefreshLayout
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
        return inflater.inflate(R.layout.fragment_fa_q, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmer_faq.isVisible = true
        swipeRefresh = view.swipe_refresh
        viewModel.getFaq()
        observerData()
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getFaq()
                shimmer_faq.isVisible = true
            }, 2000)
        }
    }
    private fun observerData() {
        viewModel.getHypno.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            list_title_rv.layoutManager = LinearLayoutManager(this.activity)
                            val listTitle = mutableListOf<ResponseHypnoDto.Data>()
                            listTitle.clear()
                            it!!.listIterator().forEach {
                                listTitle.add(it)
                            }
                            val adapter = FaqContentAdapter(requireContext(), listTitle)
                            adapter.notifyDataSetChanged()
                            list_title_rv.adapter = adapter
                        }
                        shimmer_faq.isVisible = false
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