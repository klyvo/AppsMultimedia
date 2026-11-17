package org.proven.primera_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.triler_25_26.ui.theme.Triler2526Theme

@Composable
fun PrimeraappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    Triler2526Theme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
