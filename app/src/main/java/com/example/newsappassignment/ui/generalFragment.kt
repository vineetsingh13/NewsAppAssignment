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
import com.example.newsappassignment.databinding.GeneralBinding
import com.example.newsappassignment.models.NewsSource
import com.example.newsappassignment.services.DataServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class generalFragment: Fragment(R.layout.general) {

    private lateinit var binding: GeneralBinding
    private lateinit var newsItemAdapter: NewsItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= GeneralBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            fetchGeneralHeadlines()
        }

        binding.swiperefresh.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                fetchGeneralHeadlines()
            }
        }
    }

    private suspend fun fetchGeneralHeadlines() {
        withContext(Dispatchers.Main){
            binding.swiperefresh.isRefreshing=true
        }
        val service = DataServices.buildService(newsRetrofit::class.java)
        service.getGeneralHeadlines().enqueue(object : Callback<NewsSource> {
            override fun onResponse(call: Call<NewsSource>, response: Response<NewsSource>) {
                if (response.isSuccessful) {
                    binding.recyclerview.layoutManager= LinearLayoutManager(context)
                    newsItemAdapter= NewsItemAdapter(response.body()?.sources!!)
                    binding.recyclerview.adapter=newsItemAdapter

                    binding.swiperefresh.isRefreshing=false
                } else {
                    Log.d("fetchGeneralHeadlines", "Response unsuccessful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NewsSource>, t: Throwable) {
                Log.e("fetchGeneralHeadlines", "Failed to fetch general headlines", t)
                binding.swiperefresh.isRefreshing=false
            }
        })
    }
}