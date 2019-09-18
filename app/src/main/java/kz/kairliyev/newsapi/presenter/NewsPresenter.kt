package kz.kairliyev.newsapi.presenter

import kz.kairliyev.newsapi.NewsView

interface NewsPresenter{
    fun loadMore()
    fun setView(view: NewsView)
    fun refreshPage()
    fun refreshStop()
}