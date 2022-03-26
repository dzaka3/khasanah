package com.khasanah.features.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.khasanah.R
import com.khasanah.features.di.moshi
import com.khasanah.features.dto.ResponseHypnoDto
import com.khasanah.features.utils.Constant
import com.khasanah.features.dto.TheoryDto
import kotlinx.android.synthetic.main.khasanah_item_row_title.view.*

class ListFaqAdapter(val context: Context, private val list: MutableList<ResponseHypnoDto.Data>) : RecyclerView.Adapter<ListFaqAdapter.Holder>() {

    var nomor: String? = null
    var titleContent: String? = null

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.khasanah_item_row_title, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val title = list[position]
        holder.itemView.run {
            nomor = title.id
            titleContent = title.name

            tv_title.text = titleContent
        }


        list[position].nav = R.id.menuFaqWebView

        holder.itemView.setOnClickListener {
            val bundle = bundleOf(Constant.KEY_ID_TITTLE to moshi.adapter(ResponseHypnoDto.Data::class.java).toJson(list[position]))
            holder.itemView.findNavController().navigate(list[position].nav!!, bundle)
        }
    }
}