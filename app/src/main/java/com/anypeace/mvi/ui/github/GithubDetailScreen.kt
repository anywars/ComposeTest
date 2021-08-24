package com.anypeace.mvi.ui.github

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.anypeace.mvi.model.github.Repo


@Composable
fun GithubDetailScreen(repo: Repo?) {

    Scaffold {
        GithubWebView(repo)
    }

}

@Composable
fun GithubWebView(repo: Repo?) {
    Column {
        AndroidView(factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }, update = { wv ->
            wv.loadUrl(repo?.url ?: "")
        }, modifier = Modifier.fillMaxSize())
    }
}



