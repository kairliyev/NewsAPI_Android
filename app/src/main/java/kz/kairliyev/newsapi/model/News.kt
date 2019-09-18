package kz.kairliyev.newsapi.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles") val list : ArrayList<Articles>
)