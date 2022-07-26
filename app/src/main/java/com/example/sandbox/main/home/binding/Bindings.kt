package com.example.sandbox.main.home.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.main.home.adapter.HomeAdapter
import com.example.uibox.tools.SpaceItemDecoration

@BindingAdapter("bind:initAdapter")
fun RecyclerView.initAdapter(homeAdapter: HomeAdapter) {
    hasFixedSize()
    addItemDecoration(SpaceItemDecoration(
        topDP = 6,
        leftDP = 12,
        rightDP = 12,
        bottomDP = 6,
    ))
    adapter = homeAdapter
    homeAdapter.notifyDataSetChanged()
}
