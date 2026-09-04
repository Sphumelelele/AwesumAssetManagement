package com.awesum.assetmanagement.model

data class TeamMember(
    val member_id: Int = 0,
    val job_id: Int,
    val full_name: String,
    val role: String,
    val phone: String? = null
)
