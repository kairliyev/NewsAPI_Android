package kz.kairliyev.newsapi.presenter

import android.util.Log
import kotlinx.coroutines.*
import kz.kairliyev.newsapi.NewsView
import kz.kairliyev.newsapi.model.Articles
import kz.kairliyev.newsapi.model.News
import kz.kairliyev.newsapi.utils.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NewsPresenterImpl : NewsPresenter {
    lateinit var ctx: NewsView
    override fun refreshStop() {
        ctx.refreshStop()
    }
    override fun refreshPage() {
        fetchNewsCoroutine()
    }

    override fun loadMore() {
        fetchMoreNewsCoroutine()
    }

    override fun setView(view: NewsView) {
        ctx = view
        fetchNewsCoroutine() // загружаем данные когда вызывается onCreate(), можно убрать для тестинга SwipeRefreshLayout
    }

    private fun fetchMoreNewsCoroutine() {
        ctx.showLoading()
        GlobalScope.launch {

            val response = getNews()
            withContext(Dispatchers.Main) {
                Log.d("Response", response.toString())
                ctx.showMoreNews(response)
                ctx.hideLoading()
            }
        }
    }


    private fun fetchNewsCoroutine() {
        GlobalScope.launch {
            val response = getNews()
            withContext(Dispatchers.Main) {
                Log.d("Response", response.toString())
                ctx.showNews(response)
                refreshStop()
            }
        }
    }

    private fun makeService(): NewsInterface {
        val builder = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())

        return builder.build().create(NewsInterface::class.java)
    }

    suspend fun getNews(): ArrayList<Articles> {
        val response: News
        try {
            response = makeService().getNews(
                "bitcoin",
                "2019-08-18",
                "publishedAt",
                "cb672b6a09e349288d96dcf552af1b13"
            ).await()
        } catch (e: Exception) {
            GlobalScope.launch(Dispatchers.Main) {
                ctx.showError(e.toString())
            }
            return arrayListOf()
        }
        return response.list
    }
}

interface NewsInterface {
    @GET("everything")
    fun getNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Deferred<News>
}