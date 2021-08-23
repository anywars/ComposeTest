package com.anypeace.mvi.ui.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.anypeace.mvi.api.CommonAPIService
import com.anypeace.mvi.model.Version
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class CommonViewModel @Inject constructor(
    private val service: CommonAPIService,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val TAG = CommonViewModel::class.simpleName

    val version = mutableStateOf<Version?>(null)

    fun version() {
        service.version(Version(version = "0.0.0", osType = "AND"))
            .subscribeOn(Schedulers.io())
            .subscribe({
                version.value = it.payload
            }, { e ->
                version.value = Version("x.x.x", "AND")
                Log.e(TAG, "", e)
            })
    }

    override fun onCleared() {
        super.onCleared()
    }

}