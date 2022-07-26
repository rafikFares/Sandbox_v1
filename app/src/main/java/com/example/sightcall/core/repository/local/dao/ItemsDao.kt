package com.example.sightcall.core.repository.local.dao

import com.example.sightcall.core.repository.local.entity.GitHubItemEntity

interface ItemsDao {
    suspend fun insertItem(gitHubItemEntity: GitHubItemEntity)
    suspend fun retrieveItem(searchText: String): GitHubItemEntity?
    suspend fun retrieveAllItems(): List<GitHubItemEntity>
}
