package com.example.android.SportsNews

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Html.fromHtml
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), NewsItemClickListener {

    private lateinit var mAdapter: NewsCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(my_toolbar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#696969")))
        supportActionBar?.title = fromHtml("<font color='#DCDCDC'>SPORTS NEWS</font>")

        fetchData()

        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsCustomAdapter(this)
        recyclerView.adapter = mAdapter
    }

    override fun onItemClick(item: News) {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder();
        val customTabsIntent: CustomTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

    private fun fetchData() {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val jsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val news = News(
                        jsonObject.getString("title"),
                        jsonObject.getString("author"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            { }
        )


            @Override
            @Throws(AuthFailureError::class)
            fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["X-RapidAPI-Host"] = "Your api Host"
                params["X-RapidAPI-Key"] = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx" //changedkey
                return params
            }




        MySingleton.getInstance(this).addToRequestQueue(jsonRequest)

    }
}




