package com.example.consecutivepracts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.R
import com.example.androidapp.screens.MovieDetailScreen
import com.example.consecutivepracts.components.AnimeViewModel
import com.example.consecutivepracts.model.Anime
import com.example.consecutivepracts.screens.HomeScreen
import com.example.consecutivepracts.screens.ListScreen
import com.example.consecutivepracts.screens.Screen
import com.example.consecutivepracts.screens.SettingsScreen
import com.example.consecutivepracts.ui.theme.AndroidAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
@Preview
fun MainScreen() {
    val navController = rememberNavController()
    val animeViewModel: AnimeViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = when {
        currentRoute == null -> true
        currentRoute.startsWith(Screen.Detail.title) -> false
        else -> true
    }

    val isLoading by animeViewModel.isLoading.collectAsState()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController, currentRoute = currentRoute ?: "")
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.title,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.title) { HomeScreen() }
            composable(Screen.List.title) {
                if (isLoading) {
                    FullScreenProgress()
                    return@composable
                }

                ListScreen(animeViewModel) { animeId ->
                    navController.navigate(Screen.Detail.title + "/$animeId") {
                    }
                }
            }
            composable(Screen.Settings.title) { SettingsScreen() }
            composable(Screen.Detail.title + "/{${R.string.detail_screen_id}}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("${R.string.detail_screen_id}")
                    ?.toIntOrNull()
                val anime: Anime? = id?.let {
                    animeViewModel.anime.value.find { it.id == id }
                }

                if (anime != null) {
                    MovieDetailScreen(anime, onClickPreviousButton = {
                        navController.popBackStack()
                    })
                } else {
                    Box {
                        Text(
                            text = stringResource(R.string.page_with_id_not_found),
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        ) {
                            Text(text = stringResource(R.string.rotate_to_list_screen))
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String) {
    val items = listOf(
        Screen.Home,
        Screen.List,
        Screen.Settings
    )

    NavigationBar(
        containerColor = Color(0xFF006400)

    ) {
        items.forEach { screen ->
            NavigationBarItem(
                selected = screen.title == currentRoute,
                onClick = {
                    navController.navigate(screen.title) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(screen.title, color = if (screen.title == currentRoute) Color.White else Color.LightGray) },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title,
                        tint = if (screen.title == currentRoute) Color.White else Color.LightGray
                    )
                }
            )
        }
    }
}

@Composable
fun FullScreenProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { }
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(40.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}




