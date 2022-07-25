package com.example.sightcall.main.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sightcall.main.adapter.GitHubItemsAdapter

@BindingAdapter("bind:initAdapter")
fun RecyclerView.initAdapter(gitHubItemsAdapter: GitHubItemsAdapter) {
    hasFixedSize()
    adapter = gitHubItemsAdapter
    gitHubItemsAdapter.notifyDataSetChanged()
}
