package com.khasanah.features.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.khasanah.features.adapter.ListHypnoAdapter
import com.khasanah.features.dto.ResponseHypnoDto
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.R
import kotlinx.android.synthetic.main.fragment_hypno.*
import kotlinx.android.synthetic.main.fragment_hypno.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HypnoFragment : Fragment() {

    lateinit var swipeRefresh: SwipeRefreshLayout
    private val prefs by inject<SharedPreferences>()
    private val viewModel : KhasanahViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hypno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.swipe_refresh
        viewModel.getHypno()
        observerData()
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getHypno()
                shimmer_hypno.visibility = View.VISIBLE
                list_title_rv.visibility = View.INVISIBLE
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
                            val adapter = ListHypnoAdapter(requireContext(), listTitle)
                            adapter.notifyDataSetChanged()
                            list_title_rv.adapter = adapter
                        }
                        shimmer_hypno.visibility = View.GONE
                        list_title_rv.visibility = View.VISIBLE
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