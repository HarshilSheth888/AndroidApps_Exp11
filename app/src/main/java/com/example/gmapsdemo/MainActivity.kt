package com.example.gmapsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gmapsdemo.ui.GoogleMapsScreen
import com.example.gmapsdemo.ui.theme.GMapsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GMapsDemoTheme {
                GoogleMapsScreen()
            }
        }
    }
}
