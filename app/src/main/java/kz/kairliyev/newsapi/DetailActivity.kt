package kz.kairliyev.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kz.kairliyev.newsapi.model.Articles

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        title = ""
        val json = intent.getStringExtra("article")
        val article = Gson().fromJson(json, Articles::class.java)
        initLayout(article)
    }

    private fun initLayout(article: Articles) {
        Glide.with(this).load(article.urlToImage).into(image_detail)
        news_detail_time_name.text = article.getTime
        news_detail_title_tv.text = article.title
        news_detail_author_tv.text = article.author
        news_detail_source_name.text = article.source.name
        news_detail_des_tv.text =article.description
        news_detail_content_tv.text = article.content
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
