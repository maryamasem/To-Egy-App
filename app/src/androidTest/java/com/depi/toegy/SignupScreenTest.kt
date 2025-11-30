package com.depi.toegy.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.depi.toegy.ui.theme.ToEgyTheme

@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun signUpScreen_uiElements_areDisplayed() {
        // Arrange: عرض الشاشة
        composeTestRule.setContent {
            ToEgyTheme {
                SignUpScreen()
            }
        }

        // Assert: كل العناصر موجودة
        composeTestRule.onNodeWithTag("NameTextField").assertExists()
        composeTestRule.onNodeWithTag("EmailTextField").assertExists()
        composeTestRule.onNodeWithTag("PasswordTextField").assertExists()
        composeTestRule.onNodeWithTag("PasswordToggle").assertExists()
        composeTestRule.onNodeWithTag("ConfirmPasswordTextField").assertExists()
        composeTestRule.onNodeWithTag("ConfirmPasswordToggle").assertExists()
        composeTestRule.onNodeWithTag("SignUpButton").assertExists()
    }

    @Test
    fun signUpScreen_canEnterText() {
        composeTestRule.setContent {
            ToEgyTheme {
                SignUpScreen()
            }
        }

        // Act: اكتب بيانات
        composeTestRule.onNodeWithTag("NameTextField").performTextInput("Mohamed Khafagy")
        composeTestRule.onNodeWithTag("EmailTextField").performTextInput("test@example.com")
        composeTestRule.onNodeWithTag("PasswordTextField").performTextInput("123456")
        composeTestRule.onNodeWithTag("ConfirmPasswordTextField").performTextInput("123456")

        // Assert: تأكد النصوص متاحة
        composeTestRule.onNodeWithTag("NameTextField").assertTextContains("Mohamed Khafagy")
        composeTestRule.onNodeWithTag("EmailTextField").assertTextContains("test@example.com")
        composeTestRule.onNodeWithTag("PasswordTextField").assertTextContains("123456")
        composeTestRule.onNodeWithTag("ConfirmPasswordTextField").assertTextContains("123456")
    }

    @Test
    fun signUpScreen_togglePasswordVisibility() {
        composeTestRule.setContent {
            ToEgyTheme {
                SignUpScreen()
            }
        }

        // Act: اضغط على أيقونة العين
        composeTestRule.onNodeWithTag("PasswordToggle").performClick()
        composeTestRule.onNodeWithTag("ConfirmPasswordToggle").performClick()
    }

    @Test
    fun signUpScreen_buttonIsClickable() {
        composeTestRule.setContent {
            ToEgyTheme {
                SignUpScreen()
            }
        }

        // Assert: زر التسجيل موجود ويمكن الضغط عليه
        composeTestRule.onNodeWithTag("SignUpButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SignUpButton").assertIsEnabled()
    }
}
