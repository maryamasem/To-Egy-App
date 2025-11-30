package com.depi.toegy

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.depi.toegy.model.Place
import org.junit.Rule
import org.junit.Test

// نسخة مبسطة من PlaceCard
@Composable
fun PlaceCard(place: Place, onClick: (Place) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(place) }
    ) {
        Box {
            Text(text = place.name)
        }
    }
}

class PlaceCardNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clickOnPlaceCard_navigatesToDetailScreen() {
        val context = composeTestRule.activity
        val navController = TestNavHostController(context)
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        // ضبط محتوى Compose مع NavHost
        composeTestRule.setContent {
            SetupNavGraph(navController)
        }

        // اضغط على الكارد
        composeTestRule
            .onNodeWithText("Test Place")
            .performClick()

        // اتحقق انه راح لروت التفاصيل
        assert(
            navController.currentBackStackEntry?.destination?.route?.startsWith("DetailScreen/") == true
        )
    }
}

@Composable
fun SetupNavGraph(navController: TestNavHostController) {
    NavHost(
        navController = navController,
        startDestination = "PlaceListScreen"
    ) {
        composable("PlaceListScreen") {
            // عرض كارد واحد للاختبار
            PlaceCard(
                place = Place(
                    id = "1",
                    name = "Test Place",
                    desc = "Description",
                    img = "Uri.EMPTY"
                ),
                onClick = { place ->
                    navController.navigate("DetailScreen/${place.id}")
                }
            )
        }
        composable("DetailScreen/{placeId}") {

        }
    }
}
