package com.awesum.assetmanagement.model

data class SitePhoto(
    val photo_id: Int = 0,
    val job_id: Int,
    val file_url: String,
    val caption: String? = null,
    val uploaded_at: String? = null
)
