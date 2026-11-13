package com.example.gmapsdemo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MapControls(
    onMyLocationClick: () -> Unit,
    onMapStyleClick: () -> Unit,
    isTrafficEnabled: Boolean,
    onTrafficToggle: () -> Unit,
    is3DTilted: Boolean,
    on3DTiltToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 3D Tilt FAB
        val tiltRotation by animateFloatAsState(targetValue = if (is3DTilted) 45f else 0f, label = "tilt")
        FloatingActionButton(
            onClick = on3DTiltToggle,
            shape = CircleShape,
            containerColor = if (is3DTilted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (is3DTilted) Color.White else MaterialTheme.colorScheme.onSurface,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier.size(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ViewInAr,
                contentDescription = "3D View Toggle",
                modifier = Modifier
                    .size(22.dp)
                    .rotate(tiltRotation)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Traffic Toggle FAB
        FloatingActionButton(
            onClick = onTrafficToggle,
            shape = CircleShape,
            containerColor = if (isTrafficEnabled) Color(0xFFEF4444) else MaterialTheme.colorScheme.surface,
            contentColor = if (isTrafficEnabled) Color.White else MaterialTheme.colorScheme.onSurface,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier.size(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Traffic,
                contentDescription = "Traffic Toggle",
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Map Style FAB
        FloatingActionButton(
            onClick = onMapStyleClick,
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier.size(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Layers,
                contentDescription = "Map Style",
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // My Location FAB
        FloatingActionButton(
            onClick = onMyLocationClick,
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = Modifier.size(52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "My Location",
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
