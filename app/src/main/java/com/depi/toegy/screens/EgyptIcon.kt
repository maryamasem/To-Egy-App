package com.depi.toegy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depi.toegy.ui.theme.Grey
import com.depi.toegy.ui.theme.NavyBlue
import com.depi.toegy.ui.theme.Yellow

@Composable
fun EgyptIcon(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "App icon",
            tint = Yellow,
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = "TO EGY",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = NavyBlue
        )

        Text(
            text = "Your Smart Guide in Egypt",
            color = Grey,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(24.dp))
    }
}

