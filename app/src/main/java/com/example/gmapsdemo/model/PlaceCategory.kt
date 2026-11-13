package com.example.gmapsdemo.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class PlaceCategory(
    val displayName: String,
    val icon: ImageVector,
    val accentColor: Color
) {
    ALL("All", Icons.Default.CompassCalibration, Color(0xFF6366F1)),
    CAFES("Cafes", Icons.Default.Coffee, Color(0xFFD97706)),
    RESTAURANTS("Dining", Icons.Default.Restaurant, Color(0xFFEF4444)),
    PARKS("Parks", Icons.Default.Park, Color(0xFF10B981)),
    MUSEUMS("Culture", Icons.Default.Attractions, Color(0xFF8B5CF6)),
    SHOPPING("Shopping", Icons.Default.ShoppingBag, Color(0xFFEC4899)),
    HOTELS("Hotels", Icons.Default.Hotel, Color(0xFF3B82F6)),
    GAS("Gas", Icons.Default.LocalGasStation, Color(0xFFF59E0B))
}
