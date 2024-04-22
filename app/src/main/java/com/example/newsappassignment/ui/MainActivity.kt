package com.example.newsappassignment.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsappassignment.Apis.newsRetrofit
import com.example.newsappassignment.R
import com.example.newsappassignment.adapters.NewsItemAdapter
import com.example.newsappassignment.adapters.NewsPagerAdapter
import com.example.newsappassignment.databinding.ActivityMainBinding
import com.example.newsappassignment.models.NewsSource
import com.example.newsappassignment.services.DataServices
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsItemAdapter: NewsItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter=NewsPagerAdapter(this)
        binding.viewPager.adapter=adapter

        binding.themeToggleButton.setOnCheckedChangeListener { _, isChecked ->
            val imageResId = if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.drawable.baseline_nightlight_24
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.drawable.baseline_wb_sunny_24
            }

            ObjectAnimator.ofFloat(binding.themeicon, "alpha", 1f, 0f).apply {
                duration = 300
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.themeicon.setImageResource(imageResId)
                        ObjectAnimator.ofFloat(binding.themeicon, "alpha", 0f, 1f).apply {
                            duration = 300
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                    }
                })
                start()
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "All Headlines"
                1 -> "Business"
                2 -> "Entertainment"
                3 -> "General"
                4 -> "Health"
                5 -> "Science"
                6 -> "Sports"
                7 -> "Technology"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()
        apiResponse()
    }


    private fun apiResponse(){

        val service=DataServices.buildService(newsRetrofit::class.java)

        service.getHeadlines().enqueue(object : Callback<NewsSource>{
            override fun onResponse(
                call: Call<NewsSource>,
                response: Response<NewsSource>
            ) {
                if(response.code()==200){
                    Log.d("success","data got")
                }
            }

            override fun onFailure(call: Call<NewsSource>, t: Throwable) {
                Log.d("failure","data didnt got")
            }

        })
    }

}