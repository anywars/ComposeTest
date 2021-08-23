package com.anypeace.mvi.ui.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.anypeace.mvi.api.GithubAPIService
import com.anypeace.mvi.model.Version
import com.anypeace.mvi.model.github.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class GithubViewModel @Inject constructor(
    private val repository: GithubAPIService,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val TAG = GithubAPIService::class.simpleName
    private val NETWORK_PAGE_SIZE = 30


    private val searchResults = mutableStateListOf<Repo>()


    fun requestSearchQuery(query: String): List<Repo> {
        repository.searchRepos(query, 1, NETWORK_PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .subscribe({
                searchResults.addAll(it.items)
            }, { e ->
                Log.e(TAG, "", e)
            })
        return searchResults
    }

    override fun onCleared() {
        super.onCleared()
    }


}