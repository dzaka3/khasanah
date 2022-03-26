package com.khasanah.features.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.R
import kotlinx.android.synthetic.main.fragment_menu.*
import org.koin.android.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private val viewModel : KhasanahViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.common_yes)) { dialog, id ->
                        requireActivity().moveTaskToBack(true)
                        requireActivity().finish()
                    }
                    .setNegativeButton(getString(R.string.common_no), null)
                    .show()
            }
        })

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.postDetailUser()
        observerData()
        loading_animation.isVisible = true
        card.visibility = View.INVISIBLE
        btn_akun.setOnClickListener {
            findNavController().navigate(R.id.menuDataAkun)
        }
        btn_konsep.setOnClickListener {
            findNavController().navigate(R.id.menuKonsep)
        }
        btn_pompa_asi.setOnClickListener {
            findNavController().navigate(R.id.menuPompaAsi)
        }
        btn_statistik.setOnClickListener {
            findNavController().navigate(R.id.menuStatistik)
        }
        btn_weight.setOnClickListener {
            findNavController().navigate(R.id.menuBeratBayi)
        }
        btn_hypno.setOnClickListener {
            findNavController().navigate(R.id.menuHypno)
        }
        btn_akun_baby.setOnClickListener {
            findNavController().navigate(R.id.menuDataAnak)
        }
        btn_faq.setOnClickListener {
            findNavController().navigate(R.id.menuFaq)
        }
    }

    private fun observerData() {
        viewModel.detailUser.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            data_nama.text = it?.name ?: "-"
                            data_alamat.text = it?.address ?: "-"
                            data_umur.text = it?.age ?: "-"
                            data_jumlah_anak.text = it?.childs ?: "-"
                        }
                        loading_animation.isVisible = false
                        card.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                        loading_animation.isVisible = false
                        card.visibility = View.VISIBLE
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

}