package ramirez.ruben.closetvirtual.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Tema oscuro
private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = Color.White,
    onSurface = Color.White,
    primary = PrimaryButton,
    surfaceVariant = DarkImagePlaceholder,
    outlineVariant = Color.DarkGray,
    tertiary = TagBg,
    onTertiary = Color.White
)

// Tema claro
private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primary = PrimaryButton,
    surfaceVariant = LightImagePlaceholder,
    outlineVariant = Color.LightGray,
    tertiary = TagBg,
    onTertiary = Color.Black
)

@Composable
fun ClosetVirtualTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}