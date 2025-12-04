package com.depi.toegy

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.depi.toegy.screens.EgyptIcon
import org.junit.Rule
import org.junit.Test

class EgyptIconTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun egyptIcon_showsAllElements() {
        composeRule.setContent {
            EgyptIcon()
        }
        composeRule.onNodeWithContentDescription("App icon")
            .assertIsDisplayed()
        composeRule.onNodeWithText("TO EGY")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Your Smart Guide in Egypt")
            .assertIsDisplayed()
    }
}
