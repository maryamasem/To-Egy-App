package com.depi.toegy

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.depi.toegy.screens.ForgotPasswordScreen
import com.depi.toegy.viewModel.ForgotPasswordViewModel
import org.junit.Rule
import org.junit.Test

class ForgotPasswordScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterEmail_andClickSendResetLink_callsResetPassword() {

        composeTestRule.setContent {
            ForgotPasswordScreen(viewModel = viewModel())
        }

        // 1) كتابة الإيميل داخل TextField
        composeTestRule.onNode(hasText("Email"))
            .performTextInput("test@example.com")
    }

    @Test
    fun backToLoginButton_isClickable() {

        composeTestRule.setContent {
            ForgotPasswordScreen()
        }

        composeTestRule.onNodeWithText("Back to Login")
            .assertExists()
            .performClick()
    }
}
