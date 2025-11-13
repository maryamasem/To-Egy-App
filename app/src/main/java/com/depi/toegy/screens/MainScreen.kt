package com.depi.toegy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.depi.toegy.ui.theme.AccentYellow
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
    Profile(
        route = "profile",
        icon = Icons.Default.Person,
        label = "profile",
        contentDescription = "Profile Page"
    );
    companion object {
        val entries = listOf(Home, Favourites, Profile)
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
                    Destination.Home -> Home(navController)
                    Destination.Profile -> ProfileScreen("Guest")
                    Destination.Favourites -> FavoritesScreen()
                }
            }
        }

        composable("ListScreen/{category}") {
            val category = it.arguments?.getString("category") ?: ""
            PlacesListScreen(navController,category)
        }
    }


}


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.Home
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = Color.Transparent,
                contentColor = Grey,
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    val isSelected = selectedDestination == index
                    val itemColor = if (isSelected) NavyBlue else Grey

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
                                tint = itemColor
                            )
                        },
                        label = {
                            Text((destination.label), color = itemColor)
                        },

                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = NavyBlue.copy(alpha = 0.1f) // Light background highlight
                        ),
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
                Home(navController = rememberNavController())
            }
        }
    }
}
