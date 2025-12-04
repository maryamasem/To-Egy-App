package com.depi.toegy.screens

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun Display_MapScreen(lat: Double, long: Double, name: String) {
    val decodedName = Uri.decode(name)
   // Log.d("trace", "Display_MapScreen:$decodedName lat ${lat} long $long ")
    val context = LocalContext.current
    // نحصل على الإحداثيات من الاسم
    val geocoder = Geocoder(context)
    val addresses = geocoder.getFromLocationName(decodedName, 1)

    val location = if (addresses != null && addresses.isNotEmpty()) {
        LatLng(addresses[0].latitude, addresses[0].longitude)
    } else {
        LatLng(lat, long) // fallback لو مش لاقي
    }
   // Log.d("trace", "Display_MapScreen:$location lat ${lat} long $long ")

    var mapType by remember { mutableStateOf(MapType.NORMAL) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    // Map type state
    Box(modifier = Modifier.fillMaxSize()) {

        // Google Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapType = mapType
            )
        ) {
            Marker(
                state = MarkerState(position = location),
                title  = decodedName,
                snippet = "Selected place"
            )
        }

        // ---------- Share Location Button ----------
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 90.dp, start = 16.dp),
            onClick = {
                val locationUrl = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(decodedName)}+@${lat},${long}"
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out this place: $locationUrl")
                    type = "text/plain"
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Share location via")
                )
            }
        ) {
            Icon(Icons.Default.Share, contentDescription = "Share Location")
        }

        // ---------- Change Map Type ----------
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            onClick = {
                mapType = when (mapType) {
                    MapType.NORMAL -> MapType.HYBRID
                    MapType.HYBRID -> MapType.SATELLITE
                    MapType.SATELLITE -> MapType.TERRAIN
                    else -> MapType.NORMAL
                }
            }
        ) {
            Icon(Icons.Default.Map, contentDescription = "Change Map Type")
        }

        // ---------- Directions Button ----------
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            onClick = {
                val uri = Uri.parse("google.navigation:q=$lat,$long")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                context.startActivity(intent)
            }
        ) {
            Icon(Icons.Default.Directions, contentDescription = "Directions")
        }
    }
}
