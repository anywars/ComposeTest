package com.anypeace.mvi.ui.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.anypeace.mvi.api.GithubAPIService
import com.anypeace.mvi.data.RepoDao
import com.anypeace.mvi.model.Version
import com.anypeace.mvi.model.github.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubAPIService,
    private val repoDao: RepoDao,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val TAG = GithubAPIService::class.simpleName
    private val NETWORK_PAGE_SIZE = 30
    private var page = 1
    private var isLoading = mutableStateOf(false)


    private val searchResults = mutableStateListOf<Repo>()


    fun requestSearchQuery(query: String): List<Repo> {
        page = 1
        searchResults.clear()

        searchQuery(query)
        return searchResults
    }

    fun requestRefresh(query: String) {
        isLoading.value = false
        page = 1
        searchResults.clear()

        searchQuery(query)
    }

    fun isLoading(): Boolean {
        return isLoading.value
    }

    fun requestMore(query: String) {
        if (isLoading.value) return
        searchQuery(query)
    }

    private fun searchQuery(query: String) {
        isLoading.value = true
        repository.searchRepos(query, page, NETWORK_PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .subscribe({
                searchResults.addAll(it.items)
                isLoading.value = false
                page++
            }, { e ->
                Log.e(TAG, "", e)
                isLoading.value = false
            }).apply {

            }
    }

    override fun onCleared() {
        super.onCleared()
    }


}