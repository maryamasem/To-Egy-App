package com.depi.toegy

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.depi.toegy.screens.FavoriteScreen
import com.depi.toegy.viewModel.FavoritesViewModel
import org.junit.Rule
import org.junit.Test

class FavoriteScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clickOnFavoriteCard_navigatesToDetailScreen() {
        val context = composeTestRule.activity
        val navController = TestNavHostController(context)
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // نعرض الـ FavoriteScreen مع ViewModel الحقيقي
        composeTestRule.setContent {
            val vm: FavoritesViewModel = viewModel()
            FavoriteScreen(viewModel = vm, navController = navController)
        }

        // ننتظر ظهور الـ favorite بالـ id "1" قبل الضغط
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("favorite_card_1").fetchSemanticsNode()
                true
            } catch (e: Exception) {
                false
            }
        }

        // اضغط على الكارد
        composeTestRule
            .onNodeWithTag("favorite_card_1")
            .performClick()

        // تحقق من الانتقال للـ route المتوقع
        assert(navController.currentBackStackEntry?.destination?.route?.startsWith("Place/") == true)
    }
}
