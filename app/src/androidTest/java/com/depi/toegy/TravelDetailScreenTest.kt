package com.depi.toegy

import org.junit.Test
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.depi.toegy.model.Place
import com.depi.toegy.screens.TravelDetailScreen
import org.junit.Rule
    class TravelDetailScreenTest {

        @get:Rule
        val composeRule = createComposeRule()

        private val fakePlace = Place(
            id = "1",
            name = "Test Place",
            desc = "This is a test description",
            img = "",
            lat = 30.0,
            long = 31.0,
            url = "https://example.com",
            location = "Cairo, Egypt"
        )

        @Test
        fun screen_shows_basic_content() {
            composeRule.setContent {
                TravelDetailScreen(place = fakePlace)
            }

            composeRule.onNodeWithText("Test Place").assertIsDisplayed()
            composeRule.onNodeWithText("This is a test description").assertIsDisplayed()
            composeRule.onNodeWithText("View Website/Info").assertIsDisplayed()
            composeRule.onNodeWithText("Rate Place").assertIsDisplayed()
        }

        @Test
        fun clicking_rate_button_opens_bottom_sheet() {
            composeRule.setContent {
                TravelDetailScreen(place = fakePlace)
            }

            composeRule.onNodeWithText("Rate Place").performClick()

            composeRule.onNodeWithText("Rate this Place").assertIsDisplayed()
        }

        @Test
        fun user_can_select_rating_and_write_comment() {
            val fakePlace = Place(
                id = "1",
                name = "Test Place",
                desc = "This is a test description",
                img = "",
                lat = 30.0,
                long = 31.0,
                url = "https://example.com",
                location = "Cairo, Egypt"
            )

            composeRule.setContent {
                TravelDetailScreen(place = fakePlace)
            }

            // افتح الـ BottomSheet
            composeRule.onNodeWithText("Rate Place").performClick()

            // انتظر ظهور النص داخل الـ BottomSheet
            composeRule.waitUntil(timeoutMillis = 2000) {
                composeRule.onAllNodesWithText("Rate this Place").fetchSemanticsNodes().isNotEmpty()
            }

            // اختر نجمة رقم 3
            composeRule.onNodeWithContentDescription("star_3").performClick()

            // اكتب كومنت
            composeRule.onNodeWithText("Comment...").performTextInput("Nice place!")

            // اتأكد إنه اتكتب
            composeRule.onNodeWithText("Nice place!").assertIsDisplayed()
        }

    }
