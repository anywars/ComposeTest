package com.anypeace.mvi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.anypeace.mvi.model.github.Repo
import com.anypeace.mvi.theme.ComposeTestTheme
import com.anypeace.mvi.ui.github.GithubDetailScreen
import com.anypeace.mvi.ui.github.GithubListScreen
import dagger.hilt.android.AndroidEntryPoint


sealed class Screen(val route: String) {
    object List: Screen("list")
    object Detail: Screen("detail/{id}")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            ComposeTestTheme {
                Scaffold { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.List.route,
                        modifier = Modifier.padding(padding)
                    ) {

                        composable(Screen.List.route) {
                            GithubListScreen {
                                navController.currentBackStackEntry?.arguments = bundleOf("repo" to it)
                                navController.navigate(Screen.Detail.route.replace("{id}", "${it?.id}"))
                            }
                        }

                        composable(
                            Screen.Detail.route,
                            arguments = listOf(navArgument("repo") { type = NavType.ParcelableType(Repo::class.java) })
                        ) {
                            val repo = navController.previousBackStackEntry?.arguments?.getParcelable<Repo>("repo")
                            GithubDetailScreen(repo)
                        }

                    }
                }
            }
        }
    }

}