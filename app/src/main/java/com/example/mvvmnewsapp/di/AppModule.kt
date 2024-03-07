package com.example.mvvmnewsapp.di

import android.app.Application
import android.content.Context
import android.net.Network
import androidx.room.Room
import com.example.mvvmnewsapp.api.NewsAPI
import com.example.mvvmnewsapp.db.ArticleDatabase
import com.example.mvvmnewsapp.repository.NewsRepository
import com.example.mvvmnewsapp.repository.NewsRepositoryImpl
import com.example.mvvmnewsapp.ui.NewsApp
import com.example.mvvmnewsapp.util.ConnectivityObserver
import com.example.mvvmnewsapp.util.Constants.Companion.BASE_URL
import com.example.mvvmnewsapp.util.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideConnectivityObserver(app: Application): NetworkConnectivityObserver{
        return NetworkConnectivityObserver(app.applicationContext)
    }
    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application): ArticleDatabase{
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            "article_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.
        Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesRepository(api: NewsAPI,db: ArticleDatabase): NewsRepository{
        return NewsRepositoryImpl(api,db.getArticleDao())
    }



}