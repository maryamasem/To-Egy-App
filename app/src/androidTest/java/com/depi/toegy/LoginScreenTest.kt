package com.depi.toegy

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.depi.toegy.screens.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun loginScreen_displaysUIElements() {
        rule.setContent {
            LoginScreen()
        }

        rule.onNodeWithText("Email").assertExists()
        rule.onNodeWithText("Password").assertExists()
        rule.onNodeWithText("Login").assertExists()
        rule.onNodeWithText("Forgot password?").assertExists()
        rule.onNodeWithText("Sign up").assertExists()
    }

    @Test
    fun typingEmailAndPassword_updatesTextFields() {
        rule.setContent {
            LoginScreen()
        }

        // اكتب في الإيميل
        rule.onNodeWithText("Email")
            .performTextInput("test@example.com")

        // اكتب في الباسورد
        rule.onNodeWithText("Password")
            .performTextInput("123456")

        // اتأكد إن الإيميل فعلاً اتكتب
        rule.onNodeWithText("Email")
            .assertTextContains("test@example.com")
    }


    @Test
    fun clickingButtons_isPossible() {
        rule.setContent {
            LoginScreen()
        }

        // الضغط على زرار Login
        rule.onNodeWithText("Login").performClick()

        // الضغط على Forgot password?
        rule.onNodeWithText("Forgot password?").performClick()

        // الضغط على Sign up
        rule.onNodeWithText("Sign up").performClick()
    }
}
