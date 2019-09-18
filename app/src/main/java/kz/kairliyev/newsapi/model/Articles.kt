package kz.kairliyev.newsapi.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class Articles (
	@SerializedName("source") val source : Source,
	@SerializedName("author") val author : String,
	@SerializedName("title") val title : String,
	@SerializedName("description") val description : String,
	@SerializedName("url") val url : String,
	@SerializedName("urlToImage") val urlToImage : String,
	@SerializedName("publishedAt") val publishedAt : String,
	@SerializedName("content") val content : String
) {
	val getShortDes: String
		get() = this.description.substring(0..50) + "..."
	val getTime: String
		get() {
			val time = this.publishedAt.substring(0..9)
			return time
		}
}