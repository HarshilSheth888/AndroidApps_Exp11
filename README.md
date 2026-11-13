# GMaps Demo

**Experiment 11: Integrating Google Maps in an Android App**

**Student Name:** Harshil Sheth
**USN:** 25MCAR0225

---

## Table of Contents
1. [Experiment Overview](#experiment-overview)
2. [Concept and Technology](#concept-and-technology)
3. [Scenario](#scenario)
4. [Features](#features)
5. [Project Structure](#project-structure)
6. [Getting Started](#getting-started)
7. [Output](#output)
8. [Test Cases](#test-cases)
9. [Known Limitations](#known-limitations)
10. [Learning Outcomes](#learning-outcomes)

---

## Experiment Overview

This experiment integrates Google Maps into an Android app built with Jetpack Compose. The app shows an interactive map of the San Francisco area with markers for nearby places (cafes, parks, museums, restaurants, hotels, shops, and a gas station). The user can search and filter places, tap a marker to see its details, switch between map styles, turn on traffic, and tilt the map into a 3D view.

## Concept and Technology

- **Google Maps SDK for Android** (`play-services-maps`) provides the map, camera, markers, and map styles.
- **Maps Compose** (`maps-compose`) lets the map be used as a Composable (`GoogleMap`), with `Marker` and `rememberCameraPositionState` to control what the map shows.
- **API key:** the map needs a Google Maps API key. It is supplied through a `<meta-data>` entry in `AndroidManifest.xml` that reads the `google_maps_key` string.
- **Camera control:** `CameraUpdateFactory` and `cameraPositionState.animate()` move the camera smoothly to a selected place and change the tilt for the 3D view.
- **Map types and custom styles:** Satellite and Terrain use the built-in `MapType`. Midnight Dark, Auburn Silver, and Vintage Retro are applied with JSON style rules through `MapStyleOptions`.
- **MVVM with `StateFlow`:** `MapViewModel` holds a single `MapUiState` (places, selected category, search text, selected place, map style, traffic, tilt). The UI collects it and redraws when it changes.
- **Jetpack Compose and Material 3** build the search bar, filter chips, map controls, and bottom sheets.
- **Fallback map:** `SimulatedMapView` draws a map-like canvas with roads, water, and markers, so the screen can still be demonstrated without a valid API key.

## Scenario

The user opens the app and sees the map centered on San Francisco, with a marker for each place. The user can:

- Type in the search bar to find places by name, address, or tag
- Tap a category chip (All, Cafes, Dining, Parks, Culture, Shopping, Hotels, Gas) to filter the markers
- Tap a marker to move the camera to it and open a detail sheet with rating, hours, tags, description, reviews, and Directions, Call, and Share actions
- Mark a place as a favorite
- Use the map buttons to change the map style, show traffic, tilt the map, or return to the center of the city

## Features

- Interactive Google Map with zoom, pan, rotate, and tilt gestures
- Seven sample places with ratings, hours, tags, popular times, and reviews
- Live search and category filtering
- Animated camera movement to the selected marker
- Place detail bottom sheet with favorite, call, directions, and share
- Six map themes: Standard Light, Midnight Dark, Auburn Silver, Vintage Retro, Satellite, and Terrain
- Traffic layer toggle and 3D tilt toggle
- Banner reminding the developer to add a Maps API key
- Unit tests for the ViewModel

## Project Structure

```
androidapps_exp11/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/gmapsdemo/
│   │   │   │   ├── MainActivity.kt                    # Sets the Compose content
│   │   │   │   ├── model/
│   │   │   │   │   ├── PlaceItem.kt                   # Place model and sample San Francisco data
│   │   │   │   │   ├── PlaceCategory.kt               # Category names, icons, and colors
│   │   │   │   │   └── MapStyle.kt                    # Map theme list and JSON map styles
│   │   │   │   ├── ui/
│   │   │   │   │   ├── GoogleMapsScreen.kt            # Main screen: GoogleMap, markers, camera
│   │   │   │   │   ├── viewmodel/MapViewModel.kt      # UI state and filtering logic
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── FloatingSearchBar.kt       # Search bar, results, category chips
│   │   │   │   │   │   ├── MapControls.kt             # Style, traffic, 3D, and location buttons
│   │   │   │   │   │   ├── PlaceDetailSheet.kt        # Place details bottom sheet
│   │   │   │   │   │   ├── MapStyleSelectorSheet.kt   # Map theme picker
│   │   │   │   │   │   ├── ApiKeyBanner.kt            # API key reminder banner
│   │   │   │   │   │   └── SimulatedMapView.kt        # Canvas-drawn fallback map
│   │   │   │   │   └── theme/                         # Color.kt, Type.kt, Theme.kt
│   │   │   ├── res/values/strings.xml                 # google_maps_key placeholder
│   │   │   └── AndroidManifest.xml                    # Permissions and Maps API key
│   │   ├── test/java/com/example/gmapsdemo/
│   │   │   └── MapViewModelTest.kt                    # ViewModel unit tests
│   │   └── androidTest/                               # Instrumented test template
│   └── build.gradle.kts
├── gradle/libs.versions.toml                          # Dependency versions
├── build.gradle.kts
├── settings.gradle.kts
└── screenshots/                                       # Output and test case images
```

## Getting Started

**Requirements:** Android Studio (latest stable), Android SDK, an emulator or device with Google Play services, an internet connection, and a Google Maps API key.

1. Clone the repository:
   ```bash
   git clone https://github.com/HarshilSheth888/AndroidApps_Exp11.git
   ```
2. Create a Maps SDK for Android key in the Google Cloud Console.
3. Open `app/src/main/res/values/strings.xml` and replace `YOUR_API_KEY_HERE` with your key. Do not commit your real key to GitHub.
4. Open the project in Android Studio, let Gradle sync, and press **Run**.
5. To run the unit tests, use `./gradlew test`.

## Output

![Output screenshot](screenshots/output.png)

The app shows the floating search bar with category chips at the top, the map with place markers, and the map control buttons at the bottom right.

## Test Cases

### Test Case 1: Category filter and search
- **Input:** Tap the **Cafes** chip, then clear it and type "Park" in the search bar.
- **Expected:** With Cafes selected, only cafe markers remain (Blue Bottle Coffee). With the search text "Park", the matching place (Golden Gate Park Arboretum) appears in the results and on the map.
- **Result:** ____________
- **Screenshot:** ![Test case 1](screenshots/test1.png)

### Test Case 2: Select a marker and view place details
- **Input:** Tap the marker for **SFMOMA - Museum of Modern Art**.
- **Expected:** The camera animates to the marker and zooms in, and a bottom sheet shows the name, rating, price level, opening hours, tags, description, and the Directions, Call, and Share buttons.
- **Result:** ____________
- **Screenshot:** ![Test case 2](screenshots/test2.png)

### Test Case 3: Change the map style and enable traffic
- **Input:** Tap the map style button, choose **Midnight Dark**, then tap the traffic button.
- **Expected:** The map changes to the dark theme and the sheet closes. Traffic colors appear on the roads.
- **Result:** ____________
- **Screenshot:** ![Test case 3](screenshots/test3.png)

## Known Limitations

- **The map needs a valid API key.** The project ships with the placeholder `YOUR_API_KEY_HERE`, so without a real key the map tiles do not load.
- **The API key banner is always shown.** It depends on `isApiKeyMissing`, which starts as `true` and is never set to `false` by the app.
- **The fallback `SimulatedMapView` is not reachable from the screen.** The `useSimulatedMap` flag is `false` and nothing changes it.
- **Location is simulated.** The location permissions are declared but never requested, and the My Location button moves to the sample city center rather than the device position.
- **All places are sample data** for San Francisco, not results from the Places API.

## Learning Outcomes

- Setting up the Google Maps SDK and an API key in an Android project
- Using `GoogleMap`, `Marker`, and camera state in Jetpack Compose
- Animating the camera to a location and changing tilt and zoom
- Applying map types and custom JSON map styles
- Managing screen state with a ViewModel and `StateFlow`
- Building search, filter chips, and bottom sheets in Compose
- Writing unit tests for a ViewModel

## Author

**Harshil Sheth**
USN: 25MCAR0225
GitHub: https://github.com/HarshilSheth888
