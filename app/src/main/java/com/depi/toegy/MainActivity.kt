package com.depi.toegy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.depi.toegy.screens.AuthNavHost
import com.depi.toegy.screens.MainScreen
import com.depi.toegy.ui.theme.ToEgyTheme

class MainActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToEgyTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    var isUserLoggedIn by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // التحقق من حالة تسجيل الدخول
    DisposableEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        
        // التحقق الأولي
        isUserLoggedIn = auth.currentUser != null
        isLoading = false
        
        // إضافة listener لتغييرات حالة تسجيل الدخول
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            isUserLoggedIn = firebaseAuth.currentUser != null
            isLoading = false
        }
        auth.addAuthStateListener(authStateListener)
        
        // إزالة listener عند التدمير
        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    if (isLoading) {
        // شاشة تحميل
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (isUserLoggedIn) {
            // المستخدم مسجل دخول - عرض الشاشة الرئيسية
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MainScreen(modifier = Modifier.padding(innerPadding))
            }
        } else {
            // المستخدم غير مسجل دخول - عرض شاشات المصادقة
            AuthNavHost(
                onAuthSuccess = {
                    // بعد تسجيل الدخول الناجح، سيتم تحديث isUserLoggedIn تلقائياً
                    // من خلال AuthStateListener
                }
            )
        }
    }
}