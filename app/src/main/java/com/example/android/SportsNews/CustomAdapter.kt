package com.example.android.SportsNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsCustomAdapter( private val listener: NewsItemClickListener):RecyclerView.Adapter<NewsViewHolder>(){
    private val dataset:ArrayList<News> = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.specific_view, parent, false)
        val holder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClick(dataset[holder.adapterPosition])
        }
        return holder
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.newsAuthor.text = dataset[position].author
        holder.newsDescription.text = dataset[position].title
        Glide.with(holder.itemView.context).load(dataset[position].imageUrl).into(holder.newsImage)
    }


    override fun getItemCount(): Int {
        return dataset.size
    }
    
    fun updateNews(updateditems: ArrayList<News>){
        dataset.clear()
        dataset.addAll(updateditems)

        notifyDataSetChanged()
    }



}

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val newsAuthor:TextView = itemView.findViewById(R.id.NewsAuthor)
    val newsDescription:TextView = itemView.findViewById(R.id.NewsDescription)
    val newsImage: ImageView = itemView.findViewById(R.id.NewsImage)
}

interface NewsItemClickListener {

    fun onItemClick(item: News)
}