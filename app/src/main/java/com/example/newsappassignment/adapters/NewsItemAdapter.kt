package com.example.newsappassignment.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappassignment.R
import com.example.newsappassignment.databinding.NewsViewBinding
import com.example.newsappassignment.models.newsSourceModel

class NewsItemAdapter(private val newsList: List<newsSourceModel>) :
    RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url=newsList[position].url

        holder.itemView.apply {
            alpha = 0f
            scaleX = 0.5f
            scaleY = 0.5f
            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .start()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent)
        }
        return holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}

class ViewHolder(itemView: NewsViewBinding) : RecyclerView.ViewHolder(itemView.root) {

    var title=itemView.titleTextView
    var desc=itemView.descriptionTextView

    fun bind(newsItem: newsSourceModel) {
        title.text=newsItem.name
        desc.text=newsItem.description
    }
}