package com.example.gmapsdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gmapsdemo.model.MapStyleHelper
import com.example.gmapsdemo.model.MapThemeType
import com.example.gmapsdemo.model.MockPlacesData
import com.example.gmapsdemo.model.PlaceItem
import com.example.gmapsdemo.ui.components.ApiKeyBanner
import com.example.gmapsdemo.ui.components.FloatingSearchBar
import com.example.gmapsdemo.ui.components.MapControls
import com.example.gmapsdemo.ui.components.MapStyleSelectorSheet
import com.example.gmapsdemo.ui.components.PlaceDetailSheet
import com.example.gmapsdemo.ui.components.SimulatedMapView
import com.example.gmapsdemo.ui.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsScreen(
    viewModel: MapViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val filteredPlaces = viewModel.getFilteredPlaces()
    val coroutineScope = rememberCoroutineScope()

    // Google Maps Camera Position State
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(MockPlacesData.centerLocation, 14f)
    }

    // Toggle between Google Maps SDK & Vector Canvas simulation for demo or fallback
    var useSimulatedMap by remember { mutableStateOf(false) }

    // Animate camera when selectedPlace changes
    LaunchedEffect(uiState.selectedPlace) {
        uiState.selectedPlace?.let { place ->
            coroutineScope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder()
                            .target(place.location)
                            .zoom(16f)
                            .tilt(if (uiState.is3DTilted) 45f else 0f)
                            .build()
                    ),
                    durationMs = 1000
                )
            }
        }
    }

    // Handle 3D Tilt toggle
    LaunchedEffect(uiState.is3DTilted) {
        val currentTarget = cameraPositionState.position.target
        val currentZoom = cameraPositionState.position.zoom
        coroutineScope.launch {
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(currentTarget)
                        .zoom(currentZoom)
                        .tilt(if (uiState.is3DTilted) 45f else 0f)
                        .build()
                ),
                durationMs = 600
            )
        }
    }

    // Map Properties configuration
    val mapType = when (uiState.mapThemeType) {
        MapThemeType.SATELLITE -> MapType.SATELLITE
        MapThemeType.TERRAIN -> MapType.TERRAIN
        else -> MapType.NORMAL
    }

    val mapStyleOptions: MapStyleOptions? = remember(uiState.mapThemeType) {
        MapStyleHelper.getMapStyleOptions(uiState.mapThemeType)
    }

    val mapProperties = remember(mapType, mapStyleOptions, uiState.isTrafficEnabled) {
        MapProperties(
            mapType = mapType,
            mapStyleOptions = mapStyleOptions,
            isTrafficEnabled = uiState.isTrafficEnabled
        )
    }

    val mapUiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false,
            compassEnabled = true,
            rotationGesturesEnabled = true,
            tiltGesturesEnabled = true,
            scrollGesturesEnabled = true,
            zoomGesturesEnabled = true
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Main Interactive Map View (Google Maps / Vector Simulation)
            if (useSimulatedMap) {
                SimulatedMapView(
                    places = filteredPlaces,
                    selectedPlace = uiState.selectedPlace,
                    onPlaceSelected = { place -> viewModel.selectPlace(place) },
                    themeType = uiState.mapThemeType,
                    isTrafficEnabled = uiState.isTrafficEnabled,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                    uiSettings = mapUiSettings,
                    onMapClick = {
                        viewModel.selectPlace(null)
                    }
                ) {
                    filteredPlaces.forEach { place ->
                        Marker(
                            state = MarkerState(position = place.location),
                            title = place.name,
                            snippet = "${place.category.displayName} • ★ ${place.rating}",
                            onClick = {
                                viewModel.selectPlace(place)
                                true
                            }
                        )
                    }
                }
            }

            // Top Floating Search Bar & Filter Chips
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp)
            ) {
                FloatingSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { query -> viewModel.updateSearchQuery(query) },
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = { category -> viewModel.selectCategory(category) },
                    searchResults = filteredPlaces,
                    onPlaceSelected = { place -> viewModel.selectPlace(place) }
                )

                ApiKeyBanner(
                    isVisible = uiState.isApiKeyMissing
                )
            }

            // Right Stack Map Controls FABs
            MapControls(
                onMyLocationClick = {
                    coroutineScope.launch {
                        val userLoc = uiState.userLocation ?: MockPlacesData.centerLocation
                        cameraPositionState.animate(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.Builder()
                                    .target(userLoc)
                                    .zoom(15f)
                                    .build()
                            ),
                            durationMs = 800
                        )
                    }
                },
                onMapStyleClick = {
                    viewModel.setShowStyleSelector(true)
                },
                isTrafficEnabled = uiState.isTrafficEnabled,
                onTrafficToggle = { viewModel.toggleTraffic() },
                is3DTilted = uiState.is3DTilted,
                on3DTiltToggle = { viewModel.toggle3DTilt() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 32.dp, end = 16.dp)
            )

            // Map Style Theme Selector Sheet
            if (uiState.showStyleSelector) {
                MapStyleSelectorSheet(
                    selectedTheme = uiState.mapThemeType,
                    onThemeSelected = { theme -> viewModel.setMapThemeType(theme) },
                    onDismiss = { viewModel.setShowStyleSelector(false) }
                )
            }

            // Selected Place Details Bottom Sheet
            uiState.selectedPlace?.let { place ->
                PlaceDetailSheet(
                    place = place,
                    onDismiss = { viewModel.selectPlace(null) },
                    onToggleFavorite = { id -> viewModel.toggleFavorite(id) }
                )
            }
        }
    }
}
