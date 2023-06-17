package com.fdev.fedra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fdev.fedra.domain.models.BottomNavItem
import com.fdev.fedra.ui.Navigation
import com.fdev.fedra.ui.navigation.BottomNavigationBar
import com.fdev.fedra.ui.navigation.Screens
import com.fdev.fedra.ui.theme.FedraTheme

class MainActivity : ComponentActivity() {

    private val REQUIRED_PERMISSIONS =
        arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        )

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
        setContent {
            FedraTheme {
                BottomNavBar()
            }
        }
    }

    @Composable
    fun BottomNavBar() {
        val navController = rememberNavController()
        Scaffold(bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        "Home",
                        Screens.HomeScreen.route,
                        Icons.Default.Home,
                    ),
                    BottomNavItem(
                        "Post",
                        Screens.PostScreen.route,
                        Icons.Default.Add
                    ),
                    BottomNavItem(
                        "Profile",
                        Screens.ProfileScreen.route,
                        Icons.Default.Person,
                        5
                    ),
                ),
                navController = navController,
                modifier = Modifier.fillMaxWidth(),
                onClickEvent = {
                    navController.navigate(it.route)
                },
            )
        },
            content = {
                it.calculateBottomPadding()
                Navigation(navController = navController)
            })
    }
}