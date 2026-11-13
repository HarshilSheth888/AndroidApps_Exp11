package com.example.gmapsdemo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gmapsdemo.model.MapThemeType
import com.example.gmapsdemo.model.PlaceItem
import kotlin.math.roundToInt

@Composable
fun SimulatedMapView(
    places: List<PlaceItem>,
    selectedPlace: PlaceItem?,
    onPlaceSelected: (PlaceItem) -> Unit,
    themeType: MapThemeType,
    isTrafficEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val bgColor = when (themeType) {
        MapThemeType.DARK -> Color(0xFF1E293B)
        MapThemeType.AUBURN -> Color(0xFFE2E8F0)
        MapThemeType.RETRO -> Color(0xFFFDE68A)
        MapThemeType.SATELLITE -> Color(0xFF022C22)
        MapThemeType.TERRAIN -> Color(0xFF14532D)
        else -> Color(0xFFF1F5F9)
    }

    val roadColor = when (themeType) {
        MapThemeType.DARK -> Color(0xFF334155)
        MapThemeType.SATELLITE -> Color(0xFF065F46)
        MapThemeType.RETRO -> Color(0xFFF59E0B).copy(alpha = 0.5f)
        else -> Color.White
    }

    val waterColor = when (themeType) {
        MapThemeType.DARK -> Color(0xFF0F172A)
        MapThemeType.RETRO -> Color(0xFF93C5FD)
        else -> Color(0xFF3B82F6).copy(alpha = 0.35f)
    }

    val trafficColor = Color(0xFFEF4444)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.6f, 3.0f)
                    offsetX += pan.x
                    offsetY += pan.y
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    // Tap on background clears selection
                }
            }
    ) {
        // Vector Roads & Waterways Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Draw Water body path
            val waterPath = Path().apply {
                moveTo(0f, height * 0.75f)
                cubicTo(
                    width * 0.3f, height * 0.85f,
                    width * 0.6f, height * 0.65f,
                    width, height * 0.8f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(path = waterPath, color = waterColor)

            // Main Avenues/Roads Grid
            val roadStroke = Stroke(width = 24f * scale)
            val secondaryStroke = Stroke(width = 12f * scale)

            // Horizontal Avenues
            for (i in 1..4) {
                val y = height * (i * 0.18f) + offsetY
                drawLine(
                    color = roadColor,
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = roadStroke.width
                )
                if (isTrafficEnabled && i % 2 == 0) {
                    drawLine(
                        color = trafficColor,
                        start = Offset(width * 0.2f, y),
                        end = Offset(width * 0.7f, y),
                        strokeWidth = roadStroke.width / 2
                    )
                }
            }

            // Vertical Streets
            for (j in 1..5) {
                val x = width * (j * 0.18f) + offsetX
                drawLine(
                    color = roadColor,
                    start = Offset(x, 0f),
                    end = Offset(x, height),
                    strokeWidth = secondaryStroke.width
                )
            }
        }

        // Animated Place Markers Overlay
        places.forEachIndexed { index, place ->
            val isSelected = place.id == selectedPlace?.id

            // Map lat/lng offset dynamically to grid canvas coordinates
            val posX = (300 + (index * 130) % 700 + offsetX).dp
            val posY = (240 + (index * 160) % 900 + offsetY).dp

            SimulatedMarker(
                place = place,
                isSelected = isSelected,
                onClick = { onPlaceSelected(place) },
                modifier = Modifier.offset {
                    IntOffset(posX.value.roundToInt(), posY.value.roundToInt())
                }
            )
        }
    }
}

@Composable
private fun SimulatedMarker(
    place: PlaceItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaleAnim by animateFloatAsState(
        targetValue = if (isSelected) 1.25f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "markerScale"
    )

    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (isSelected) MaterialTheme.colorScheme.primary else place.category.accentColor,
        tonalElevation = if (isSelected) 12.dp else 6.dp,
        shadowElevation = if (isSelected) 12.dp else 6.dp,
        modifier = modifier
            .scale(scaleAnim)
            .size(if (isSelected) 48.dp else 40.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = place.category.icon,
                contentDescription = place.name,
                tint = Color.White,
                modifier = Modifier.size(if (isSelected) 26.dp else 22.dp)
            )
        }
    }
}
