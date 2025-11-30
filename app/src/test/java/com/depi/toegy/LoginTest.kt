package com.depi.toegy

import org.junit.Assert.assertEquals
import org.junit.Test

// ✅ Helper function للتحقق من بيانات تسجيل الدخول
fun validateLogin(email: String, password: String): String? {
    return when {
        email.isBlank() -> "Please enter your email"
        password.isBlank() -> "Please enter your password"
        else -> null
    }
}

class LoginValidationTest {

    @Test
    fun testEmptyEmail() {
        val error = validateLogin("", "123456")
        assertEquals("Please enter your email", error)
    }

    @Test
    fun testEmptyPassword() {
        val error = validateLogin("test@mail.com", "")
        assertEquals("Please enter your password", error)
    }

    @Test
    fun testValidInput() {
        val error = validateLogin("test@mail.com", "123456")
        assertEquals(null, error)
    }
}
