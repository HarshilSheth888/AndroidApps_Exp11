package com.example.gmapsdemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gmapsdemo.model.MockPlacesData
import com.example.gmapsdemo.model.MapThemeType
import com.example.gmapsdemo.model.PlaceCategory
import com.example.gmapsdemo.model.PlaceItem
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MapUiState(
    val places: List<PlaceItem> = MockPlacesData.samplePlaces,
    val selectedCategory: PlaceCategory = PlaceCategory.ALL,
    val searchQuery: String = "",
    val selectedPlace: PlaceItem? = null,
    val mapThemeType: MapThemeType = MapThemeType.STANDARD,
    val isTrafficEnabled: Boolean = false,
    val is3DTilted: Boolean = false,
    val userLocation: LatLng? = MockPlacesData.centerLocation,
    val isApiKeyMissing: Boolean = true,
    val showStyleSelector: Boolean = false
)

class MapViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun selectCategory(category: PlaceCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = category,
                selectedPlace = null
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    fun selectPlace(place: PlaceItem?) {
        _uiState.update { currentState ->
            currentState.copy(selectedPlace = place)
        }
    }

    fun toggleFavorite(placeId: String) {
        _uiState.update { currentState ->
            val updatedPlaces = currentState.places.map { place ->
                if (place.id == placeId) {
                    place.copy(isFavorite = !place.isFavorite)
                } else place
            }
            val updatedSelected = currentState.selectedPlace?.let {
                if (it.id == placeId) it.copy(isFavorite = !it.isFavorite) else it
            }
            currentState.copy(
                places = updatedPlaces,
                selectedPlace = updatedSelected
            )
        }
    }

    fun setMapThemeType(theme: MapThemeType) {
        _uiState.update { currentState ->
            currentState.copy(
                mapThemeType = theme,
                showStyleSelector = false
            )
        }
    }

    fun toggleTraffic() {
        _uiState.update { currentState ->
            currentState.copy(isTrafficEnabled = !currentState.isTrafficEnabled)
        }
    }

    fun toggle3DTilt() {
        _uiState.update { currentState ->
            currentState.copy(is3DTilted = !currentState.is3DTilted)
        }
    }

    fun setShowStyleSelector(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(showStyleSelector = show)
        }
    }

    fun setApiKeyMissingState(missing: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isApiKeyMissing = missing)
        }
    }

    fun updateUserLocation(latLng: LatLng) {
        _uiState.update { currentState ->
            currentState.copy(userLocation = latLng)
        }
    }

    fun getFilteredPlaces(): List<PlaceItem> {
        val state = _uiState.value
        return state.places.filter { place ->
            val matchesCategory = (state.selectedCategory == PlaceCategory.ALL) || (place.category == state.selectedCategory)
            val matchesQuery = state.searchQuery.isBlank() ||
                    place.name.contains(state.searchQuery, ignoreCase = true) ||
                    place.address.contains(state.searchQuery, ignoreCase = true) ||
                    place.tags.any { it.contains(state.searchQuery, ignoreCase = true) }
            matchesCategory && matchesQuery
        }
    }
}
