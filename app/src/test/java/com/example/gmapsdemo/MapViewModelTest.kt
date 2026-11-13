package com.example.gmapsdemo

import com.example.gmapsdemo.model.MapThemeType
import com.example.gmapsdemo.model.PlaceCategory
import com.example.gmapsdemo.ui.viewmodel.MapViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MapViewModelTest {

    private lateinit var viewModel: MapViewModel

    @Before
    fun setUp() {
        viewModel = MapViewModel()
    }

    @Test
    fun testInitialState() {
        val state = viewModel.uiState.value
        assertEquals(PlaceCategory.ALL, state.selectedCategory)
        assertEquals("", state.searchQuery)
        assertNull(state.selectedPlace)
        assertEquals(MapThemeType.STANDARD, state.mapThemeType)
        assertFalse(state.isTrafficEnabled)
        assertFalse(state.is3DTilted)
        assertTrue(state.places.isNotEmpty())
    }

    @Test
    fun testSelectCategoryFiltersPlaces() {
        viewModel.selectCategory(PlaceCategory.CAFES)
        val filtered = viewModel.getFilteredPlaces()
        assertTrue(filtered.all { it.category == PlaceCategory.CAFES })
    }

    @Test
    fun testSearchQueryFiltersPlaces() {
        viewModel.updateSearchQuery("Coffee")
        val filtered = viewModel.getFilteredPlaces()
        assertTrue(filtered.any { it.name.contains("Coffee", ignoreCase = true) })
    }

    @Test
    fun testSelectPlaceUpdatesState() {
        val samplePlace = viewModel.uiState.value.places.first()
        viewModel.selectPlace(samplePlace)
        assertEquals(samplePlace, viewModel.uiState.value.selectedPlace)
    }

    @Test
    fun testToggleFavorite() {
        val samplePlace = viewModel.uiState.value.places.first()
        val initialFav = samplePlace.isFavorite
        viewModel.toggleFavorite(samplePlace.id)
        val updatedPlace = viewModel.uiState.value.places.first { it.id == samplePlace.id }
        assertEquals(!initialFav, updatedPlace.isFavorite)
    }

    @Test
    fun testToggleMapThemeAndControls() {
        viewModel.setMapThemeType(MapThemeType.DARK)
        assertEquals(MapThemeType.DARK, viewModel.uiState.value.mapThemeType)

        viewModel.toggleTraffic()
        assertTrue(viewModel.uiState.value.isTrafficEnabled)

        viewModel.toggle3DTilt()
        assertTrue(viewModel.uiState.value.is3DTilted)
    }
}
