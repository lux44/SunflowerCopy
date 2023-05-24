package com.example.ksptest.compose.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.ksptest.R
import com.example.ksptest.data.Plant
import com.example.ksptest.databinding.HomeScreenBinding

enum class SunFlowerPage(
    @StringRes val titleResId : Int,
    @DrawableRes val drawableResId : Int
) {
   MY_GARDEN(R.string.my_garden_title, R.drawable.ic_my_garden_active),
   PLANT_LIST(R.string.plant_list_title, R.drawable.ic_plant_list_active)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Plant) -> Unit = {},
    onPageChange: (SunFlowerPage) -> Unit = {},
    onAttached: (Toolbar) -> Unit = {},
){
    val activity = (LocalContext.current as AppCompatActivity)

    AndroidViewBinding(factory = HomeScreenBinding::inflate, modifier = modifier) {
        onAttached(toolbar)
        activity.setSupportActionBar(toolbar)

        compose.setContent {
            HomePagerScreen(onPlantClick = onPlantClick, onPageChange = onPageChange)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerScreen(
    onPlantClick: (Plant) -> Unit ,
    onPageChange: (SunFlowerPage) -> Unit,
    modifier: Modifier = Modifier,
    pages: Array<SunFlowerPage> = SunFlowerPage.values()
){

}