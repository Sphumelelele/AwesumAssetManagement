package com.awesum.assetmanagement.model

data class Asset(
    val asset_id: Int = 0,
    val job_id: Int,
    val name: String,
    val category: String,   // Dispatch | Equipment | Vehicle
    val status: String,     // In Use | Out for Service
    val quantity: Int = 1,
    val notes: String? = null
)
