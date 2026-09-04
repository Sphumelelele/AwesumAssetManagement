package com.awesum.assetmanagement.model

data class Job(
    val job_id: Int,
    val job_code: String,
    val user_id: Int,
    val status: String,
    val installation_address: String,
    val start_time: String,
    val end_time: String?
)
