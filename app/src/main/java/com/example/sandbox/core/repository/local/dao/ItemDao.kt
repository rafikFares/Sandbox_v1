package com.example.sandbox.core.repository.local.dao

import com.example.sandbox.core.repository.local.entity.NodeEntity

interface ItemDao {
    suspend fun insertItem(nodeEntity: NodeEntity)
    suspend fun retrieveItem(searchText: String): NodeEntity?
    suspend fun retrieveAllItems(): List<NodeEntity>
}
