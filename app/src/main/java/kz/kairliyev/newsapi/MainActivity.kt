package kz.kairliyev.newsapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kairliyev.newsapi.adapter.NewsAdapter
import kz.kairliyev.newsapi.model.Articles
import kz.kairliyev.newsapi.presenter.NewsPresenter
import kz.kairliyev.newsapi.presenter.NewsPresenterImpl
import kz.kairliyev.newsapi.utils.PaginationRecycler

class MainActivity : AppCompatActivity(), NewsView, SwipeRefreshLayout.OnRefreshListener {
    lateinit var presenter: NewsPresenter
    lateinit var paginationRecycler: PaginationRecycler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_swipe_refresh.setOnRefreshListener(this)
        presenter = NewsPresenterImpl()
        presenter.setView(this)
        initLayout()
    }

    private fun initLayout() {
        list_news.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        list_news.layoutManager = layoutManager
        list_news.adapter = NewsAdapter(this)
        paginationRecycler = object : PaginationRecycler(layoutManager) {
            override fun nextPage(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loadNextDataFromApi()
            }
        }
        list_news.addOnScrollListener(paginationRecycler)
    }

    private fun loadNextDataFromApi() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading()
            withContext(Dispatchers.IO) {
                presenter.loadMore()
            }
        }
    }

    override fun showLoading() {
        list_pb_bottom.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        list_pb_bottom.visibility = View.GONE
    }

    override fun showNews(news: ArrayList<Articles>) {
        Log.d("Main", news.toString())
        (list_news.adapter as NewsAdapter).addNews(news)
    }

    override fun showMoreNews(news: ArrayList<Articles>) {
        (list_news.adapter as NewsAdapter).addMoreNews(news)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onRefresh() {
        main_swipe_refresh.isRefreshing = true
        presenter.refreshPage()
    }

    override fun refreshStop() {
        main_swipe_refresh.isRefreshing = false
    }
}
