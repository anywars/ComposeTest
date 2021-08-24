package com.anypeace.mvi.ui.github

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anypeace.mvi.R
import com.anypeace.mvi.model.github.Repo
import com.anypeace.mvi.ui.common.InfiniteListHandler
import com.anypeace.mvi.ui.model.GithubViewModel
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


private var list: List<Repo> = arrayListOf()

@Composable
fun GithubListScreen(onItemClick:((repo: Repo?) -> Unit)? = null) {
    val query = "Android"
    val vm = hiltViewModel<GithubViewModel>()
    if (list.isEmpty()) {
        list = vm.requestSearchQuery(query)
    }

    Column {
        GithubListView(
            repositories = list,
            onItemClick = onItemClick,
            onRefresh = { vm.requestRefresh(query) },
            onMore = { vm.requestMore(query) }
        )
    }
}

@Composable
fun GithubListView(
    repositories: List<Repo>,
    isLoading: Boolean = false,
    onRefresh:(() -> Unit)? = null,
    onMore:(() -> Unit)? = null,
    onItemClick:((repo: Repo?) -> Unit)? = null,
) {
    val state: LazyListState = rememberLazyListState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = { onRefresh?.invoke() }
    ) {
        LazyColumn(state = state, modifier = Modifier.fillMaxSize()) {
//            stickyHeader {
//                Text("====================")
//            }

            if (repositories.isNotEmpty()) {
                items(items = repositories, key = { it.id }) { repo ->
                    GithubView(repo = repo) {
                        onItemClick!!(repo)
                    }
                    Divider()
                }
            }

            else {
                items(5) {
                    GithubView(
                        Repo(0, "*****", "*****", "*****", "", 0, 0, "*******"),
                        isPlaceholder = true
                    )
                }
            }
        }
    }

    InfiniteListHandler(state) {
        onMore?.invoke()
    }
}

@Composable
fun GithubView(repo: Repo? = null, isPlaceholder: Boolean = false, onClick:(() -> Unit)? = null) {

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .selectable(true,
                onClick = { onClick?.invoke() })
    ) {
        Text(text = repo?.name ?: "",
            fontSize = 24.sp,
            style = TextStyle(color = colorResource(id = R.color.titleColor)),
            modifier = Modifier
                .placeholder(visible = isPlaceholder),
        )

        Text(text = repo?.description ?: "",
            fontSize = 12.sp,
            maxLines = 10,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .placeholder(visible = isPlaceholder)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = repo?.language ?: "",
                fontSize = 16.sp,
                modifier = Modifier
                    .placeholder(visible = isPlaceholder)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "",
                modifier = Modifier
                    .size(14.dp)
                    .placeholder(visible = isPlaceholder)
            )
            Text(text = "${repo?.stars}",
                fontSize = 12.sp,
                modifier = Modifier
                    .placeholder(visible = isPlaceholder)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_git_branch),
                contentDescription = "",
                modifier = Modifier
                    .size(14.dp)
                    .padding(start = 5.dp)
                    .placeholder(visible = isPlaceholder)
            )
            Text(text = "${repo?.forks}",
                fontSize = 12.sp,
                modifier = Modifier
                    .placeholder(visible = isPlaceholder)
            )
        }

    }
}




@Preview
@Composable
fun Preview() {

    val repo = Repo(0, "dddd", "dvdvdvd", "vvvvv", "dddddddd", 0, 0, "ddddd")

    GithubView(repo)

}