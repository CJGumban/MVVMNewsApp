package com.example.mvvmnewsapp.repository

import androidx.lifecycle.LiveData
import com.example.mvvmnewsapp.models.Article
import com.example.mvvmnewsapp.models.NewsResponse
import retrofit2.Response

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>

    suspend fun upsert(article: Article): Long

    fun getSavedNews() : LiveData<List<Article>>

    suspend fun deleteArticle(article: Article)

}