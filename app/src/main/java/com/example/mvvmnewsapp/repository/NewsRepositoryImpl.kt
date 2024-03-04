package com.example.mvvmnewsapp.repository

import com.example.mvvmnewsapp.api.NewsAPI
import com.example.mvvmnewsapp.db.ArticleDao
import com.example.mvvmnewsapp.models.Article
import com.example.mvvmnewsapp.models.NewsResponse
import retrofit2.Response

class NewsRepositoryImpl(
    val api: NewsAPI,
    val articleDao: ArticleDao
): NewsRepository {
    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return api.getBreakingNews(countryCode, pageNumber)
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>{
        return api.searchForNews(searchQuery, pageNumber)
    }

    override suspend fun upsert(article: Article) = articleDao.upsert(article)

    override fun getSavedNews() = articleDao.getAllArticles()

    override suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)
}