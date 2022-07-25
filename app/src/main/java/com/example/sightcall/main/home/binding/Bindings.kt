package com.example.sightcall.main.home.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sightcall.main.home.adapter.GitHubItemsAdapter

@BindingAdapter("bind:initAdapter")
fun RecyclerView.initAdapter(gitHubItemsAdapter: GitHubItemsAdapter) {
    hasFixedSize()
    adapter = gitHubItemsAdapter
    gitHubItemsAdapter.notifyDataSetChanged()
}
