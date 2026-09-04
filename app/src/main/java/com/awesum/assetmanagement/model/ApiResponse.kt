package com.awesum.assetmanagement.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)
