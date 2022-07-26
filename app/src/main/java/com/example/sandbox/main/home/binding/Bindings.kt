package com.example.sandbox.main.home.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.main.home.adapter.HomeAdapter

@BindingAdapter("bind:initAdapter")
fun RecyclerView.initAdapter(homeAdapter: HomeAdapter) {
    hasFixedSize()
    adapter = homeAdapter
    homeAdapter.notifyDataSetChanged()
}
