package com.depi.toegy.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun EgyptIcon(modifier: Modifier = Modifier) {
    // أيقونة دبوس أحمر (location pin)
    Canvas(
        modifier = modifier
            .size(120.dp)
            .padding(bottom = 8.dp)
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2f
        
        // اللون الأحمر للدبوس
        val redColor = Color(0xFFE53935) // أحمر
        val darkRedColor = Color(0xFFC62828) // أحمر غامق للظل
        
        // شكل الدبوس - دائرة في الأعلى مع مثلث مدبب في الأسفل
        val circleRadius = width * 0.25f
        val circleCenterY = height * 0.35f
        val pinPointY = height * 0.92f
        val pinWidth = circleRadius * 0.6f
        
        val pinPath = Path().apply {
            // الجزء الدائري العلوي (رأس الدبوس)
            addOval(
                Rect(
                    offset = Offset(centerX - circleRadius, circleCenterY - circleRadius),
                    size = Size(circleRadius * 2, circleRadius * 2)
                )
            )
            
            // الجزء المدبب السفلي (نهاية الدبوس)
            moveTo(centerX - pinWidth, circleCenterY + circleRadius)
            lineTo(centerX, pinPointY)
            lineTo(centerX + pinWidth, circleCenterY + circleRadius)
            close()
        }
        
        // رسم الظل الخفيف
        val shadowPath = Path().apply {
            addOval(
                Rect(
                    offset = Offset(centerX - circleRadius + 1f, circleCenterY - circleRadius + 1f),
                    size = Size(circleRadius * 2, circleRadius * 2)
                )
            )
            
            moveTo(centerX - pinWidth + 1f, circleCenterY + circleRadius + 1f)
            lineTo(centerX + 1f, pinPointY + 2f)
            lineTo(centerX + pinWidth + 1f, circleCenterY + circleRadius + 1f)
            close()
        }
        
        // رسم الظل أولاً
        drawPath(
            path = shadowPath,
            color = darkRedColor.copy(alpha = 0.3f)
        )
        
        // رسم الدبوس باللون الأحمر
        drawPath(
            path = pinPath,
            color = redColor
        )
        
        // إضافة دائرة بيضاء صغيرة في المركز (تمثل نقطة الموقع)
        val innerCircleRadius = width * 0.08f
        drawCircle(
            color = Color.White,
            radius = innerCircleRadius,
            center = Offset(centerX, circleCenterY)
        )
    }
}

