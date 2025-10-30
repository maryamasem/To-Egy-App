package com.depi.toegy.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.depi.toegy.ui.theme.BackgroundWhite
import com.depi.toegy.ui.theme.Grey
import com.depi.toegy.ui.theme.NavyBlue
import com.depi.toegy.ui.theme.ToEgyTheme

enum class Destination(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val contentDescription: String
) {
    Home(
        route = "home",
        icon = Icons.Default.Home,
        label = "Home",
        contentDescription = "Home Page"
    ),
    Favourites(
        route = "favourites",
        icon = Icons.Default.FavoriteBorder,
        label = "Favourites",
        contentDescription = "Favourites Page"
    ),
    Setting(
        route = "setting",
        icon = Icons.Default.Settings,
        label = "Setting",
        contentDescription = "Setting Page"
    );

    companion object {
        val entries = listOf(Home, Favourites, Setting)
    }

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.Home -> Home()
                    Destination.Setting -> Setting()
                    Destination.Favourites -> FavoritesScreen()
                }
            }
        }
    }


}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val startDestination = Destination.Home
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    Scaffold(
        containerColor = BackgroundWhite,
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = BackgroundWhite,
                contentColor = NavyBlue,
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    val isSelected = selectedDestination == index

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription,
                                tint = if (isSelected) NavyBlue else Grey
                            )
                        },
                        label = {
                            Text((destination.label), color = if (isSelected) NavyBlue else Grey)
                        },
                        alwaysShowLabel = true
                    )
                }

            }
        }

    ) { contentPadding ->
        AppNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(contentPadding)
        )
    }


}

@Preview(showSystemUi = true)
@Composable
fun MainScreenStaticPreview() {
    ToEgyTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    Destination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = destination == Destination.Home,
                            onClick = {},
                            icon = {
                                Icon(
                                    destination.icon,
                                    contentDescription = destination.contentDescription
                                )
                            },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Home()
            }
        }
    }
}
