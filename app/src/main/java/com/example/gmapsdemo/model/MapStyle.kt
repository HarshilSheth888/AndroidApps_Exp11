package com.example.gmapsdemo.model

import com.google.android.gms.maps.model.MapStyleOptions

enum class MapThemeType(val displayName: String) {
    STANDARD("Standard Light"),
    DARK("Midnight Dark"),
    AUBURN("Auburn Silver"),
    RETRO("Vintage Retro"),
    SATELLITE("Satellite View"),
    TERRAIN("Terrain Contour")
}

object MapStyleHelper {

    fun getMapStyleOptions(themeType: MapThemeType): MapStyleOptions? {
        val jsonString = when (themeType) {
            MapThemeType.STANDARD -> null
            MapThemeType.DARK -> DARK_STYLE_JSON
            MapThemeType.AUBURN -> AUBURN_STYLE_JSON
            MapThemeType.RETRO -> RETRO_STYLE_JSON
            MapThemeType.SATELLITE -> null
            MapThemeType.TERRAIN -> null
        }
        return jsonString?.let { MapStyleOptions(it) }
    }

    // Midnight Dark Mode JSON style
    private val DARK_STYLE_JSON = """
        [
          {"elementType": "geometry", "stylers": [{"color": "#242f3e"}]},
          {"elementType": "labels.text.stroke", "stylers": [{"color": "#242f3e"}]},
          {"elementType": "labels.text.fill", "stylers": [{"color": "#746855"}]},
          {"featureType": "administrative.locality", "elementType": "labels.text.fill", "stylers": [{"color": "#d59563"}]},
          {"featureType": "poi", "elementType": "labels.text.fill", "stylers": [{"color": "#d59563"}]},
          {"featureType": "poi.park", "elementType": "geometry", "stylers": [{"color": "#263c3f"}]},
          {"featureType": "poi.park", "elementType": "labels.text.fill", "stylers": [{"color": "#6b9a76"}]},
          {"featureType": "road", "elementType": "geometry", "stylers": [{"color": "#38414e"}]},
          {"featureType": "road", "elementType": "geometry.stroke", "stylers": [{"color": "#212a37"}]},
          {"featureType": "road", "elementType": "labels.text.fill", "stylers": [{"color": "#9ca5b3"}]},
          {"featureType": "road.highway", "elementType": "geometry", "stylers": [{"color": "#746855"}]},
          {"featureType": "road.highway", "elementType": "geometry.stroke", "stylers": [{"color": "#1f2835"}]},
          {"featureType": "road.highway", "elementType": "labels.text.fill", "stylers": [{"color": "#f3d19c"}]},
          {"featureType": "transit", "elementType": "geometry", "stylers": [{"color": "#2f3948"}]},
          {"featureType": "transit.station", "elementType": "labels.text.fill", "stylers": [{"color": "#d59563"}]},
          {"featureType": "water", "elementType": "geometry", "stylers": [{"color": "#17263c"}]},
          {"featureType": "water", "elementType": "labels.text.fill", "stylers": [{"color": "#515c6d"}]},
          {"featureType": "water", "elementType": "labels.text.stroke", "stylers": [{"color": "#17263c"}]}
        ]
    """.trimIndent()

    // Auburn Silver JSON style
    private val AUBURN_STYLE_JSON = """
        [
          {"elementType": "geometry", "stylers": [{"color": "#f5f5f5"}]},
          {"elementType": "labels.icon", "stylers": [{"visibility": "off"}]},
          {"elementType": "labels.text.fill", "stylers": [{"color": "#616161"}]},
          {"elementType": "labels.text.stroke", "stylers": [{"color": "#f5f5f5"}]},
          {"featureType": "administrative.land_parcel", "elementType": "labels.text.fill", "stylers": [{"color": "#bdbdbd"}]},
          {"featureType": "poi", "elementType": "geometry", "stylers": [{"color": "#eeeeee"}]},
          {"featureType": "poi", "elementType": "labels.text.fill", "stylers": [{"color": "#757575"}]},
          {"featureType": "poi.park", "elementType": "geometry", "stylers": [{"color": "#e5e8e8"}]},
          {"featureType": "road", "elementType": "geometry", "stylers": [{"color": "#ffffff"}]},
          {"featureType": "road.arterial", "elementType": "labels.text.fill", "stylers": [{"color": "#757575"}]},
          {"featureType": "road.highway", "elementType": "geometry", "stylers": [{"color": "#dadada"}]},
          {"featureType": "road.highway", "elementType": "labels.text.fill", "stylers": [{"color": "#616161"}]},
          {"featureType": "transit", "elementType": "geometry", "stylers": [{"color": "#e0e0e0"}]},
          {"featureType": "water", "elementType": "geometry", "stylers": [{"color": "#c9c9c9"}]},
          {"featureType": "water", "elementType": "labels.text.fill", "stylers": [{"color": "#9e9e9e"}]}
        ]
    """.trimIndent()

    // Vintage Retro JSON style
    private val RETRO_STYLE_JSON = """
        [
          {"elementType": "geometry", "stylers": [{"color": "#ebe3cd"}]},
          {"elementType": "labels.text.fill", "stylers": [{"color": "#523735"}]},
          {"elementType": "labels.text.stroke", "stylers": [{"color": "#f5f1e6"}]},
          {"featureType": "administrative", "elementType": "geometry.stroke", "stylers": [{"color": "#c9b2a6"}]},
          {"featureType": "administrative.land_parcel", "elementType": "geometry.stroke", "stylers": [{"color": "#dcd2be"}]},
          {"featureType": "administrative.land_parcel", "elementType": "labels.text.fill", "stylers": [{"color": "#ae9e90"}]},
          {"featureType": "landscape.natural", "elementType": "geometry", "stylers": [{"color": "#dfd2ae"}]},
          {"featureType": "poi", "elementType": "geometry", "stylers": [{"color": "#dfd2ae"}]},
          {"featureType": "poi", "elementType": "labels.text.fill", "stylers": [{"color": "#93817c"}]},
          {"featureType": "poi.park", "elementType": "geometry", "stylers": [{"color": "#a5b076"}]},
          {"featureType": "road", "elementType": "geometry", "stylers": [{"color": "#f5f1e6"}]},
          {"featureType": "road.arterial", "elementType": "geometry", "stylers": [{"color": "#fdfcf8"}]},
          {"featureType": "road.highway", "elementType": "geometry", "stylers": [{"color": "#f8c967"}]},
          {"featureType": "road.highway", "elementType": "geometry.stroke", "stylers": [{"color": "#e9ac3a"}]},
          {"featureType": "transit.line", "elementType": "geometry", "stylers": [{"color": "#dfd2ae"}]},
          {"featureType": "transit.line", "elementType": "labels.text.fill", "stylers": [{"color": "#8f7d77"}]},
          {"featureType": "water", "elementType": "geometry", "stylers": [{"color": "#b9d3c2"}]}
        ]
    """.trimIndent()
}
