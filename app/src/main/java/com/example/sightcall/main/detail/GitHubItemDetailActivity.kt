package com.example.sightcall.main.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sightcall.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubItemDetailActivity : AppCompatActivity() {
    private val gitHubItemDetailViewModel by viewModel<GitHubItemDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
