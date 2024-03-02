package com.example.mvvmnewsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.models.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view){
        var btSaveArticle: Button
        var ivArticleImage: ImageView
        var tvAuthor: TextView
        var tvTitle: TextView
        var tvPublishedAt: TextView
        init {

            btSaveArticle = view.findViewById(R.id.btSaveArticle)
            ivArticleImage = view.findViewById(R.id.ivArticleImage)
            tvAuthor = view.findViewById(R.id.tvAuthor)
            tvTitle = view.findViewById(R.id.tvTitle)
            tvPublishedAt = view.findViewById(R.id.tvPublishedAt)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NewsAdapter.ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.apply {
            Glide.with(this.itemView).load(article.urlToImage).into(ivArticleImage)
            tvAuthor.text = article.author
            tvTitle.text = article.title
            tvPublishedAt.text = article.publishedAt
            btSaveArticle.setOnClickListener {
                Log.i("NewsAdapter", "btSaveArticle Clicked: ${article.title}")
            }
            holder.itemView.setOnClickListener {
                Log.i("NewsAdapter", "itemviewclicked : ${article.title}")
                onItemClickListener?.let { it(article) }
            }

        }

    }
    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}