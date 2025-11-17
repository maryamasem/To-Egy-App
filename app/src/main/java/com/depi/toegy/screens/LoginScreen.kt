package com.depi.toegy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depi.toegy.R
import com.depi.toegy.ui.theme.Grey
import com.depi.toegy.ui.theme.NavyBlue
import com.depi.toegy.ui.theme.ToEgyTheme
import com.depi.toegy.ui.theme.Yellow

@Composable
fun LoginScreen() {
    // حالات الحقول
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconPlaceholder()

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TO EGY",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )

            Text(
                text = "Your Smart Guide in Egypt",
                color = Grey,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFCED6E2),
                    unfocusedIndicatorColor = Color(0xFFECEFF4),
                    focusedContainerColor = Color(0xFFF5F7FA),
                    unfocusedContainerColor = Color(0xFFF5F7FA)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFCED6E2),
                    unfocusedIndicatorColor = Color(0xFFECEFF4),
                    focusedContainerColor = Color(0xFFF5F7FA),
                    unfocusedContainerColor = Color(0xFFF5F7FA)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // زر تسجيل الدخول
            Button(
                onClick = { /* تحقق من المصادقة هنا */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyBlue)
            ) {
                Text(text = "Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { /* نسيت كلمة المرور؟ */ }) {
                Text(text = "Forgot password?", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don’t have account? ")
                TextButton(onClick = { /* Sign up action */ }) {
                    Text(text = "Sign up", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun IconPlaceholder() {
    Icon(
        imageVector = Icons.Default.LocationOn,
        contentDescription = "App icon",
        tint = Yellow,
        modifier = Modifier.size(40.dp)
    )
}

@Preview(
    showBackground = true,
    name = "Login Screen Preview",
    widthDp = 360,
    heightDp = 640
)
@Composable
fun LoginScreenPreview() {
    ToEgyTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            LoginScreen()
        }
    }
}

@Preview(
    showBackground = true,
    name = "Icon Placeholder Preview"
)
@Composable
fun IconPlaceholderPreview() {
    ToEgyTheme {
        IconPlaceholder()
    }
}