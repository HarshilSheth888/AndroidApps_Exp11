package com.example.gmapsdemo.model

import com.google.android.gms.maps.model.LatLng

data class ReviewItem(
    val authorName: String,
    val rating: Float,
    val timeAgo: String,
    val comment: String
)

data class PlaceItem(
    val id: String,
    val name: String,
    val category: PlaceCategory,
    val location: LatLng,
    val address: String,
    val rating: Float,
    val reviewsCount: Int,
    val priceLevel: String,
    val description: String,
    val isOpenNow: Boolean,
    val openingHours: String,
    val distanceKm: Double,
    val isFavorite: Boolean = false,
    val phoneNumber: String = "+1 (555) 019-2834",
    val websiteUrl: String = "https://maps.google.com",
    val tags: List<String> = emptyList(),
    val popularTimes: List<Int> = listOf(20, 45, 80, 95, 85, 60, 30), // 12pm - 6pm occupancy percentages
    val reviews: List<ReviewItem> = emptyList()
)

object MockPlacesData {
    // San Francisco Bay Area default center point: 37.7749, -122.4194
    val centerLocation = LatLng(37.7749, -122.4194)

    val samplePlaces = listOf(
        PlaceItem(
            id = "p1",
            name = "Blue Bottle Coffee",
            category = PlaceCategory.CAFES,
            location = LatLng(37.7762, -122.4231),
            address = "315 Linden St, San Francisco, CA",
            rating = 4.7f,
            reviewsCount = 1280,
            priceLevel = "$$",
            description = "Artisanal espresso drinks, pour-over single-origin roasts, and fresh morning pastries in a sleek, minimalist space.",
            isOpenNow = true,
            openingHours = "7:00 AM – 6:00 PM",
            distanceKm = 0.4,
            isFavorite = true,
            tags = listOf("Outdoor Seating", "Espresso Bar", "Pastries", "Free Wi-Fi"),
            popularTimes = listOf(30, 60, 95, 85, 70, 40, 20),
            reviews = listOf(
                ReviewItem("Sarah K.", 5.0f, "2 days ago", "The New Orleans Iced Coffee here is legendary! Staff is super friendly."),
                ReviewItem("David M.", 4.5f, "1 week ago", "Great pour-overs, though seating can be limited during peak morning hours.")
            )
        ),
        PlaceItem(
            id = "p2",
            name = "Golden Gate Park Arboretum",
            category = PlaceCategory.PARKS,
            location = LatLng(37.7694, -122.4662),
            address = "1199 9th Ave, San Francisco, CA",
            rating = 4.9f,
            reviewsCount = 3450,
            priceLevel = "Free",
            description = "Sprawling botanical botanical gardens with rare plant collections, tranquil lily ponds, and scenic woodland trails.",
            isOpenNow = true,
            openingHours = "7:30 AM – 5:00 PM",
            distanceKm = 2.1,
            isFavorite = false,
            tags = listOf("Botanical Gardens", "Family Friendly", "Photography", "Quiet Zone"),
            popularTimes = listOf(15, 35, 70, 90, 80, 50, 25),
            reviews = listOf(
                ReviewItem("Elena R.", 5.0f, "Yesterday", "Incredible plant diversity! A peaceful sanctuary right in the middle of the city."),
                ReviewItem("Marcus T.", 5.0f, "3 days ago", "Magnificent magnolias in bloom. Don't miss the cloud forest section!")
            )
        ),
        PlaceItem(
            id = "p3",
            name = "SFMOMA - Museum of Modern Art",
            category = PlaceCategory.MUSEUMS,
            location = LatLng(37.7857, -122.4011),
            address = "151 3rd St, San Francisco, CA",
            rating = 4.8f,
            reviewsCount = 4820,
            priceLevel = "$$$",
            description = "Seven floors of world-class modern and contemporary art exhibitions, rooftop sculpture gardens, and interactive design displays.",
            isOpenNow = true,
            openingHours = "10:00 AM – 5:00 PM",
            distanceKm = 1.2,
            isFavorite = true,
            tags = listOf("Exhibitions", "Sculpture Garden", "Gift Shop", "Cafe In-House"),
            popularTimes = listOf(10, 40, 85, 100, 90, 60, 30),
            reviews = listOf(
                ReviewItem("Arthur C.", 5.0f, "5 days ago", "World class art museum! The living wall in the sculpture garden is stunning."),
                ReviewItem("Chloe B.", 4.0f, "2 weeks ago", "Expansive exhibits. Budget at least 3 hours to take it all in.")
            )
        ),
        PlaceItem(
            id = "p4",
            name = "State Bird Provisions",
            category = PlaceCategory.RESTAURANTS,
            location = LatLng(37.7837, -122.4338),
            address = "1529 Fillmore St, San Francisco, CA",
            rating = 4.7f,
            reviewsCount = 2910,
            priceLevel = "$$$$",
            description = "Innovative dim sum–style service featuring inventive California cuisine, small plates, and roasted quail specialties.",
            isOpenNow = false,
            openingHours = "5:30 PM – 10:00 PM",
            distanceKm = 1.8,
            isFavorite = false,
            tags = listOf("Michelin Star", "Small Plates", "Wine Pairings", "Reservations"),
            popularTimes = listOf(0, 0, 20, 85, 100, 90, 40),
            reviews = listOf(
                ReviewItem("Liam W.", 5.0f, "4 days ago", "Unbelievable culinary experience. Every cart that came by had something spectacular."),
                ReviewItem("Jessica P.", 5.0f, "1 month ago", "The sourdough pancakes with sauerkraut were mindblowing.")
            )
        ),
        PlaceItem(
            id = "p5",
            name = "Palace Hotel & Suites",
            category = PlaceCategory.HOTELS,
            location = LatLng(37.7881, -122.4019),
            address = "2 New Montgomery St, San Francisco, CA",
            rating = 4.6f,
            reviewsCount = 1890,
            priceLevel = "$$$$",
            description = "Historic luxury hotel featuring Austrian crystal chandeliers, glass-domed atrium garden court dining, and indoor lap pool.",
            isOpenNow = true,
            openingHours = "24 Hours",
            distanceKm = 1.1,
            isFavorite = false,
            tags = listOf("Historic Landmark", "Luxury Suites", "Indoor Pool", "Valet Parking"),
            popularTimes = listOf(40, 50, 60, 65, 70, 60, 50),
            reviews = listOf(
                ReviewItem("Robert F.", 5.0f, "1 week ago", "Breakfast under the glass dome was unforgettable. Impeccable service."),
                ReviewItem("Anita S.", 4.0f, "3 weeks ago", "Grand architecture and very comfortable rooms right downtown.")
            )
        ),
        PlaceItem(
            id = "p6",
            name = "Union Square Shopping Arcade",
            category = PlaceCategory.SHOPPING,
            location = LatLng(37.7879, -122.4074),
            address = "333 Post St, San Francisco, CA",
            rating = 4.5f,
            reviewsCount = 5600,
            priceLevel = "$$$",
            description = "Vibrant urban shopping district with high-end designer boutiques, department stores, art galleries, and seasonal events.",
            isOpenNow = true,
            openingHours = "10:00 AM – 8:00 PM",
            distanceKm = 0.9,
            isFavorite = false,
            tags = listOf("Boutiques", "Department Stores", "Pedestrian Plaza", "BART Access"),
            popularTimes = listOf(20, 50, 80, 90, 85, 70, 40),
            reviews = listOf(
                ReviewItem("Sophia L.", 4.5f, "3 days ago", "Fun atmosphere, especially during holidays when the tree and ice rink are up."),
                ReviewItem("Kevin Z.", 4.0f, "1 week ago", "Great place for window shopping and people watching.")
            )
        ),
        PlaceItem(
            id = "p7",
            name = "CleanEnergy Shell Station",
            category = PlaceCategory.GAS,
            location = LatLng(37.7712, -122.4128),
            address = "1201 Harrison St, San Francisco, CA",
            rating = 4.2f,
            reviewsCount = 340,
            priceLevel = "$$",
            description = "Convenient 24/7 service station equipped with EV fast chargers, air/water pumps, gourmet snack mart, and car wash.",
            isOpenNow = true,
            openingHours = "24 Hours",
            distanceKm = 0.8,
            isFavorite = false,
            tags = listOf("24/7 Open", "EV Fast Charging", "Car Wash", "Snack Mart"),
            popularTimes = listOf(30, 40, 50, 60, 55, 45, 30),
            reviews = listOf(
                ReviewItem("Dan H.", 4.0f, "2 weeks ago", "Clean pumps and quick EV charger availability."),
                ReviewItem("Sam R.", 4.0f, "1 month ago", "Well lit and clean convenience store.")
            )
        )
    )
}
