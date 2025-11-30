package com.depi.toegy

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.depi.toegy.screens.Home
import org.junit.Rule
import org.junit.Test

class HomeScreenCompleteUITest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun test_Home_UI_Elements_Visible() {
        rule.setContent {
            val nav = rememberNavController()
            Home(nav)
        }

        // Titles
        rule.onNodeWithText("Popular attraction").assertExists()
        rule.onNodeWithText("Categories").assertExists()

        // Card Title
        rule.onNodeWithText("Egyptian Museum").assertExists()

        // Categories
        val categories = listOf("Museums","Beaches","Restaurant","Hotels","History","Airports")
        categories.forEach { label ->
            rule.onNodeWithText(label).assertExists()
        }

        // Banner Button
        rule.onNodeWithText("Show Details").assertExists()
    }


    @Test
    fun test_BannerButton_Clickable() {
        rule.setContent {
            val nav = rememberNavController()
            Home(nav)
        }

        // Just check clickability (UI test only)
        rule.onNodeWithText("Show Details")
            .assertExists()
            .assertHasClickAction()
            .performClick()
    }
    @Test
    fun test_Categories_Visible_And_Clickable() {
        // الحالة لتخزين آخر Route
        var navRoute = mutableStateOf<String?>(null)

        rule.setContent {
            val nav = rememberNavController()

            // Listener لتحديث navRoute عند أي تغيير
            nav.addOnDestinationChangedListener { _, destination, _ ->
                navRoute.value = destination.route
            }

            // NavHost مع Home وDestination وهمية لكل Category
            NavHost(navController = nav, startDestination = "home") {
                composable("home") { Home(nav) }
                composable("ListScreen/{type}") {} // Dummy destination
            }
        }

        // كل Categories
        val categories = listOf("Museums","Beaches","Restaurant","Hotels","History","Airports")

        categories.forEach { label ->
            // اتأكد إن العنصر موجود
            rule.onNodeWithText(label)
                .assertExists()
                .assertHasClickAction()   // قابلية الكليك

        }
    }
}
