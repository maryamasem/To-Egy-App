package com.depi.toegy

import org.junit.Assert.assertEquals
import org.junit.Test

// ✅ Helper function لنقلنا من SignUpScreen للتحقق من البيانات
fun validateSignUp(
    name: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    return when {
        name.isBlank() -> "Please enter your name"
        email.isBlank() -> "Please enter your email"
        password.isBlank() -> "Please enter your password"
        password.length < 6 -> "Password must be at least 6 characters"
        password != confirmPassword -> "Passwords do not match"
        else -> null
    }
}

class SignUpValidationTest {

    @Test
    fun testEmptyName() {
        val error = validateSignUp("", "test@mail.com", "123456", "123456")
        assertEquals("Please enter your name", error)
    }

    @Test
    fun testEmptyEmail() {
        val error = validateSignUp("John Doe", "", "123456", "123456")
        assertEquals("Please enter your email", error)
    }

    @Test
    fun testEmptyPassword() {
        val error = validateSignUp("John Doe", "test@mail.com", "", "")
        assertEquals("Please enter your password", error)
    }

    @Test
    fun testShortPassword() {
        val error = validateSignUp("John Doe", "test@mail.com", "123", "123")
        assertEquals("Password must be at least 6 characters", error)
    }

    @Test
    fun testPasswordMismatch() {
        val error = validateSignUp("John Doe", "test@mail.com", "123456", "654321")
        assertEquals("Passwords do not match", error)
    }

    @Test
    fun testValidInput() {
        val error = validateSignUp("John Doe", "test@mail.com", "123456", "123456")
        assertEquals(null, error)
    }
}
