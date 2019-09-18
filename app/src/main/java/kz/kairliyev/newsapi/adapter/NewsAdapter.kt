package kz.kairliyev.newsapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_news.view.*
import kz.kairliyev.newsapi.DetailActivity
import kz.kairliyev.newsapi.R
import kz.kairliyev.newsapi.model.Articles

class NewsAdapter(private val context: Context?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var news: ArrayList<Articles> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
        return ViewHolder(root)
    }

    fun addNews(news: ArrayList<Articles>?) {
        if (news?.size == 0 || news != null) {
            this.news.clear()
            for (i in 0..9) {
                this.news.add(news[i])
            }
            notifyDataSetChanged()
        }
    }

    fun addMoreNews(news: ArrayList<Articles>?) {
        if (news?.size == 0 || news != null) {
            for (i in 0..9) {
                this.news.add(news[i])
            }
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, DetailActivity::class.java)
                    val json = Gson().toJson(news[adapterPosition])
                    intent.putExtra("article", json)
                    context?.let { it1 ->
                        startActivity(it1, intent, null)
                    }
                }
            }
        }
            fun bind(news: Articles) = with(itemView) {
                news_title.text = news.title
                news_des.text = news.getShortDes
//            Glide.with(context).load(movie.getPosterUrl()).into(poster)
            }
        }


    }