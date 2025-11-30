package com.depi.toegy

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

fun validateEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email is required"
        !email.contains("@") -> "Invalid email format"
        else -> null // لا خطأ
    }
}
interface ForgotPasswordService {
    fun sendResetLink(email: String): Boolean
}

class MockForgotPasswordService : ForgotPasswordService {
    var calledEmail: String? = null
    var shouldSucceed: Boolean = true

    override fun sendResetLink(email: String): Boolean {
        calledEmail = email
        return shouldSucceed
    }
}


class ForgotPasswordUnitTest {

    // -------------------
    // Validation Tests
    // -------------------
    @Test
    fun `empty email returns error`() {
        val result = validateEmail("")
        assertEquals("Email is required", result)
    }

    @Test
    fun `invalid email returns error`() {
        val result = validateEmail("invalidemail")
        assertEquals("Invalid email format", result)
    }

    @Test
    fun `valid email returns null`() {
        val result = validateEmail("test@mail.com")
        assertNull(result)
    }

    // -------------------
    // Mock Service Tests
    // -------------------
    @Test
    fun `sendResetLink called with correct email`() {
        val mockService = MockForgotPasswordService()
        val email = "test@mail.com"

        val result = mockService.sendResetLink(email)

        assertTrue(result)                     // تحقق من نجاح العملية
        assertEquals(email, mockService.calledEmail)  // تحقق من أن الفنكشن استُدعي بالـ email الصحيح
    }

    @Test
    fun `sendResetLink failure returns false`() {
        val mockService = MockForgotPasswordService()
        mockService.shouldSucceed = false

        val result = mockService.sendResetLink("fail@mail.com")

        assertFalse(result)
        assertEquals("fail@mail.com", mockService.calledEmail)
    }
}
