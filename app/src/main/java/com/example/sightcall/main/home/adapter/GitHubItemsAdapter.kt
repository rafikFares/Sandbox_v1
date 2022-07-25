package com.example.sightcall.main.home.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.main.home.GitHubItemsViewModel
import com.example.uibox.tools.StringSource
import com.example.uibox.view.ItemGitHubView

class GitHubItemsAdapter(private val gitHubItemsViewModel: GitHubItemsViewModel) :
    ListAdapter<GitHubItem, GitHubItemsAdapter.GitHubItemViewHolder>(DiffUtilItemCallback()) {

    private class DiffUtilItemCallback : DiffUtil.ItemCallback<GitHubItem>() {
        override fun areItemsTheSame(
            oldItem: GitHubItem,
            newItem: GitHubItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GitHubItem,
            newItem: GitHubItem
        ): Boolean {
            return oldItem == newItem // it is data classes, it will check the equals
        }
    }

    inner class GitHubItemViewHolder(private val view: ItemGitHubView) : RecyclerView.ViewHolder(view) {

        fun bind(gitHubItem: GitHubItem) {
            view.configure(gitHubItem.createItemGitHubData()) {
                gitHubItemsViewModel.handleItemClick(gitHubItem)
            }
        }

        private fun GitHubItem.createItemGitHubData() =
            ItemGitHubView.ItemGitHubData(
                itemName = StringSource.String(repositoryName),
                itemLanguage = StringSource.String(repositoryLanguage),
                itemOwner = StringSource.String(repositoryOwner),
                itemAvatarUrl = repositoryOwnerAvatarUrl
            )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubItemsAdapter.GitHubItemViewHolder =
        ItemGitHubView(parent.context).apply {
            layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }.let {
            GitHubItemViewHolder(it)
        }

    override fun onBindViewHolder(holder: GitHubItemsAdapter.GitHubItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size
}
