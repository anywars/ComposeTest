package com.anypeace.mvi.model.github

import com.google.gson.annotations.SerializedName


data class RepoSearchResult(
    @SerializedName("total_count") val total: Long = 0,
    @SerializedName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)
