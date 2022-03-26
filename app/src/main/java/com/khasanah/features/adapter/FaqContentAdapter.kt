package com.khasanah.features.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khasanah.R
import com.khasanah.features.dto.ResponseHypnoDto
import kotlinx.android.synthetic.main.item_list_content.view.*

class FaqContentAdapter(val context: Context, private val list: MutableList<ResponseHypnoDto.Data>) : RecyclerView.Adapter<FaqContentAdapter.Holder>() {

    var nomor: String? = null
    var titleContent: String? = null
    var descContent: String? = null

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val title = list[position]
        holder.itemView.run {
            nomor = title.id
            titleContent = title.name
            descContent = title.description

            title_content_tv.text = titleContent
            detail_content_tv.text = descContent
        }

        holder.view.see_detail.setOnClickListener {
            holder.view.detail_content_tv.visibility = View.VISIBLE
        }

        holder.view.detail_content_tv.setOnClickListener {
            holder.view.detail_content_tv.visibility = View.GONE
        }
    }
}