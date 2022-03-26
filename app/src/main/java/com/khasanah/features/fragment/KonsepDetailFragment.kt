package com.khasanah.features.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.khasanah.R
import com.khasanah.features.di.moshi
import com.khasanah.features.utils.Constant
import com.khasanah.features.dto.TheoryDto
import kotlinx.android.synthetic.main.fragment_konsep_detail.*
import kotlinx.android.synthetic.main.fragment_konsep_detail.btn_back
import org.koin.android.ext.android.inject

class KonsepDetailFragment : Fragment() {

    private val prefs by inject<SharedPreferences>()
    var titleModel = TheoryDto.Data()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it -> titleModel =
            moshi.adapter(TheoryDto.Data::class.java).fromJson(it.getString(Constant.KEY_ID_TITTLE))!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_konsep_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        val headerMap = HashMap<String,String>()
        headerMap["Authorization"] = "Bearer ".plus(prefs.getString(Constant.KEY_TOKEN, ""))

        web_view.loadUrl(Constant.BASE_URL.plus("theory/").plus(titleModel.id), headerMap)
        web_view.webViewClient = WebViewClient()

    }

}