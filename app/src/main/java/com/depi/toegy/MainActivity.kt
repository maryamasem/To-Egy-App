package com.depi.toegy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.depi.toegy.navigation.AppNavigation
import com.depi.toegy.ui.theme.ToEgyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToEgyTheme {
                AppNavigation()
            }
        }
    }
}