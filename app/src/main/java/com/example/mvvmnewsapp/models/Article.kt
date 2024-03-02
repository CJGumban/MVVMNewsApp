package com.example.mvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
/*id is nullable we get a lot of articles from retrofit but not all will be saved
* only saved articles needs an id*/
data class Article(

    var id: Int? = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    @PrimaryKey
    val url: String = "",
    val urlToImage: String?
) : Serializable{
    override fun hashCode(): Int {
        var result = id.hashCode()
        if (url.isNullOrEmpty()){
            result = 31 * result + url.hashCode()
        }
        return result
    }
}