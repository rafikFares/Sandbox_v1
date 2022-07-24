package com.example.sightcall.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sightcall.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubItemsActivity : AppCompatActivity() {

    private val gitHubItemsViewModel by viewModel<GitHubItemsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gitHubItemsViewModel.loadData()
    }
}
