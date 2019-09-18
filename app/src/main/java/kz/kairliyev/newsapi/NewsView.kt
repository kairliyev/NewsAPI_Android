package kz.kairliyev.newsapi

import kz.kairliyev.newsapi.model.Articles

interface NewsView{
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
    fun showNews(news: ArrayList<Articles>)
    fun showMoreNews(news: ArrayList<Articles>)
    fun refreshStop()
}