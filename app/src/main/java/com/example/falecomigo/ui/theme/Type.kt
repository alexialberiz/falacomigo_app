package com.example.falecomigo.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.falecomigo.R

// Set of Material typography styles to start with
val Poppins = FontFamily(
    Font(R.font.poppins_regular)
)

val Typography = Typography(
    defaultFontFamily = Poppins,
    h1 = TextStyle(fontSize = 30.sp),
    body1 = TextStyle(fontSize = 18.sp)
    // você pode adicionar mais estilos se quiser
)
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */