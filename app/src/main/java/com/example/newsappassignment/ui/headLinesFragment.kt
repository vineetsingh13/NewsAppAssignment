package com.example.newsappassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappassignment.Apis.newsRetrofit
import com.example.newsappassignment.R
import com.example.newsappassignment.adapters.NewsItemAdapter
import com.example.newsappassignment.databinding.HeadlinesBinding
import com.example.newsappassignment.databinding.HealthBinding
import com.example.newsappassignment.models.NewsSource
import com.example.newsappassignment.services.DataServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class headLinesFragment: Fragment(R.layout.headlines) {

    private lateinit var binding: HeadlinesBinding
    private lateinit var newsItemAdapter: NewsItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= HeadlinesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            fetchHeadlines()
        }

        binding.swiperefresh.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                fetchHeadlines()
            }
        }
    }

    private suspend fun fetchHeadlines() {
        withContext(Dispatchers.Main){
            binding.swiperefresh.isRefreshing=true
        }
        val service = DataServices.buildService(newsRetrofit::class.java)
        service.getHeadlines().enqueue(object : Callback<NewsSource> {
            override fun onResponse(call: Call<NewsSource>, response: Response<NewsSource>) {
                if (response.isSuccessful) {
                    binding.recyclerview.layoutManager= LinearLayoutManager(context)
                    newsItemAdapter= NewsItemAdapter(response.body()?.sources!!)
                    binding.recyclerview.adapter=newsItemAdapter
                    binding.swiperefresh.isRefreshing=false
                } else {
                    Log.d("fetchHeadlines", "Response unsuccessful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NewsSource>, t: Throwable) {
                Log.e("fetchHeadlines", "Failed to fetch headlines", t)
                binding.swiperefresh.isRefreshing=false
            }
        })
    }
}