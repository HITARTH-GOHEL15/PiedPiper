package org.example.project.design


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// MockMate Brand Colors
object MockMateColors {
    val Primary = Color(0xFF8B5CF6) // Purple-500
    val PrimaryVariant = Color(0xFF7C3AED) // Purple-600
    val Secondary = Color(0xFF06B6D4) // Cyan-500
    val SecondaryVariant = Color(0xFF0891B2) // Cyan-600
    val Accent = Color(0xFF10B981) // Emerald-500
    val Success = Color(0xFF059669) // Emerald-600
    val Warning = Color(0xFFF59E0B) // Amber-500
    val Error = Color(0xFFEF4444) // Red-500

    // Background Colors
    val Background = Color(0xFFFAF9FF) // Light purple tint
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF3F4F6)

    // Dark Theme Colors
    val BackgroundDark = Color(0xFF1A1B23)
    val SurfaceDark = Color(0xFF2D2E36)
    val SurfaceVariantDark = Color(0xFF404148)

    // Text Colors
    val OnPrimary = Color.White
    val OnSecondary = Color.White
    val OnBackground = Color(0xFF1F2937)
    val OnSurface = Color(0xFF1F2937)
    val OnSurfaceVariant = Color(0xFF6B7280)

    // Dark Text Colors
    val OnBackgroundDark = Color(0xFFF9FAFB)
    val OnSurfaceDark = Color(0xFFF9FAFB)
    val OnSurfaceVariantDark = Color(0xFF9CA3AF)
}

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = MockMateColors.Primary,
    onPrimary = MockMateColors.OnPrimary,
    primaryContainer = MockMateColors.Primary.copy(alpha = 0.1f),
    onPrimaryContainer = MockMateColors.Primary,
    secondary = MockMateColors.Secondary,
    onSecondary = MockMateColors.OnSecondary,
    secondaryContainer = MockMateColors.Secondary.copy(alpha = 0.1f),
    onSecondaryContainer = MockMateColors.Secondary,
    tertiary = MockMateColors.Accent,
    onTertiary = Color.White,
    tertiaryContainer = MockMateColors.Accent.copy(alpha = 0.1f),
    onTertiaryContainer = MockMateColors.Accent,
    error = MockMateColors.Error,
    onError = Color.White,
    errorContainer = MockMateColors.Error.copy(alpha = 0.1f),
    onErrorContainer = MockMateColors.Error,
    background = MockMateColors.Background,
    onBackground = MockMateColors.OnBackground,
    surface = MockMateColors.Surface,
    onSurface = MockMateColors.OnSurface,
    surfaceVariant = MockMateColors.SurfaceVariant,
    onSurfaceVariant = MockMateColors.OnSurfaceVariant,
    outline = MockMateColors.OnSurfaceVariant.copy(alpha = 0.3f),
    outlineVariant = MockMateColors.OnSurfaceVariant.copy(alpha = 0.1f)
)

// Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = MockMateColors.Primary,
    onPrimary = MockMateColors.OnPrimary,
    primaryContainer = MockMateColors.PrimaryVariant,
    onPrimaryContainer = Color.White,
    secondary = MockMateColors.Secondary,
    onSecondary = MockMateColors.OnSecondary,
    secondaryContainer = MockMateColors.SecondaryVariant,
    onSecondaryContainer = Color.White,
    tertiary = MockMateColors.Accent,
    onTertiary = Color.White,
    tertiaryContainer = MockMateColors.Success,
    onTertiaryContainer = Color.White,
    error = MockMateColors.Error,
    onError = Color.White,
    errorContainer = MockMateColors.Error.copy(alpha = 0.2f),
    onErrorContainer = MockMateColors.Error.copy(alpha = 0.8f),
    background = MockMateColors.BackgroundDark,
    onBackground = MockMateColors.OnBackgroundDark,
    surface = MockMateColors.SurfaceDark,
    onSurface = MockMateColors.OnSurfaceDark,
    surfaceVariant = MockMateColors.SurfaceVariantDark,
    onSurfaceVariant = MockMateColors.OnSurfaceVariantDark,
    outline = MockMateColors.OnSurfaceVariantDark.copy(alpha = 0.3f),
    outlineVariant = MockMateColors.OnSurfaceVariantDark.copy(alpha = 0.1f)
)

@Composable
fun MockMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MockMateTypography,
        shapes = MockMateShapes,
        content = content
    )
}

// Typography
val MockMateTypography = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default
    ),
    headlineLarge = Typography().headlineLarge.copy(
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
    ),
    titleLarge = Typography().titleLarge.copy(
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
    ),
    bodyLarge = Typography().bodyLarge.copy(
        lineHeight = 24.sp
    )
)

// Shapes
val MockMateShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
)