package com.example.ksptest.compose

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ksptest.R
import com.example.ksptest.compose.gallery.GalleryScreen
import com.example.ksptest.compose.home.HomeScreen
import com.example.ksptest.compose.home.SunFlowerPage
import com.example.ksptest.compose.plantdetail.PlantDetailScreen

@Composable
fun SunflowerApp(
    onPageChange: (SunFlowerPage) -> Unit = {},
    onAttached: (Toolbar) -> Unit = {}
) {
    val navController = rememberNavController()
    SUnFlowerNavHost(
        navController = navController,
        onPageChange = onPageChange,
        onAttached = onAttached
    )
}

@Composable
fun SUnFlowerNavHost(
    navController: NavHostController,
    onPageChange: (SunFlowerPage) -> Unit = {},
    onAttached: (Toolbar) -> Unit = {}
){
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPlantClick = {
                   navController.navigate("plantDetail/${it.plantId}")
                },
                onPageChange = onPageChange,
                onAttached = onAttached
            )
        }
        composable(
            "plantDetail/{plantId}",
            arguments = listOf(navArgument("plantId") {
                type = NavType.StringType
            })
        ) {
            PlantDetailScreen(
                onBackClick = { navController.navigateUp() },
                onShareClick = {
                    createShareIntent(activity, it)
                },
                onGalleryClick = {
                    navController.navigate("gallery/${it.name}")
                }
            )
        }

        composable(
            "gallery/{plantName}",
            arguments = listOf(navArgument("plantName"){
                type = NavType.StringType
            })
        ) {
            GalleryScreen(
                onPhotoClick ={
                    val uri = Uri.parse(it.user.attributionUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    activity.startActivity(intent)
                },
                onUpClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}

private fun createShareIntent(activity: Activity, plantName:String) {
    val shareText = activity.getString(R.string.share_text_plant, plantName)
    val shareIntent = ShareCompat.IntentBuilder(activity)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}