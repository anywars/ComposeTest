package com.anypeace.mvi.ui.common

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun InfiniteListHandler(
    state: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val info = state.layoutInfo
            val totalCount = info.totalItemsCount
            val lastVisibleIdx = (info.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            totalCount > 0 && lastVisibleIdx > (totalCount - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect { onLoadMore() }
    }
}