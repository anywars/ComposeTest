package com.anypeace.mvi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.anypeace.mvi.R
import com.anypeace.mvi.model.Version
import com.anypeace.mvi.model.github.Repo
import com.anypeace.mvi.theme.ComposeTestTheme
import com.anypeace.mvi.ui.model.CommonViewModel
import com.anypeace.mvi.ui.model.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val vm = viewModels<CommonViewModel>()
    val gitVm = viewModels<GithubViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = gitVm.value.requestSearchQuery("Android")

        setContent {
            ComposeTestTheme {
                Surface(color = MaterialTheme.colors.background) {
//                    VersionInfo(vm.value.version.value)
                    GithubList(repositories = list)
                }
            }
        }

    }
}


@Composable
fun VersionInfo(version: Version?) {

    Column {
        Text(text = version?.version ?: "")

        Row {
            Text(text = version?.osType ?: "")
            Spacer(Modifier.size(10.dp))
            Text(text = version?.marketUrl ?: "")
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GithubList(repositories: List<Repo>) {
    LazyColumn() {
        stickyHeader {
            Text("====================")
        }

        items(repositories) { repo ->
            GithubView(repo = repo)
            Divider()
        }
    }
}


@Composable
fun GithubView(repo: Repo) {

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(text = repo.name,
            fontSize = 24.sp,
            style = TextStyle(color = colorResource(id = R.color.titleColor))
        )

        Text(text = repo.description ?: "",
            fontSize = 12.sp,
            maxLines = 10,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = repo.language ?: "",
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "",
                modifier = Modifier.size(14.dp)
            )
            Text(text = "${repo.stars}",
                fontSize = 12.sp,
            )

            Image(
                painter = painterResource(id = R.drawable.ic_git_branch),
                contentDescription = "",
                modifier = Modifier.size(14.dp).padding(start = 5.dp)
            )
            Text(text = "${repo.forks}",
                fontSize = 12.sp
            )
        }

    }
}



@Preview(showBackground = true, name = "test")
@Composable
fun DefaultPreview() {
    val version = Version("0.0.0", "AND", "memo","market://details?id=test")

    val list = arrayListOf<Repo>()
    for (i in 0..10) {
        list.add(Repo(0L, "test", "lee test", "desc desc desc desc", "http://m.naver.com", 10, 1, "ko-KR"))
    }

    ComposeTestTheme {
        Surface(color = MaterialTheme.colors.background) {
            GithubList(repositories = list)
        }
    }
}



