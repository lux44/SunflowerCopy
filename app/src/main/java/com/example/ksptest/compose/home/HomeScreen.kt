package com.example.ksptest.compose.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.ksptest.R
import com.example.ksptest.compose.garden.GardenScreen
import com.example.ksptest.data.Plant
import com.example.ksptest.databinding.HomeScreenBinding
import com.google.accompanist.themeadapter.material.MdcTheme
import kotlinx.coroutines.launch

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
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage){
        onPageChange(pages[pagerState.currentPage])
    }

    Column(modifier.nestedScroll(rememberNestedScrollInteropConnection())) {
        val coroutineScope = rememberCoroutineScope()

        //Tab Row
        TabRow(selectedTabIndex = pagerState.currentPage) {
            pages.forEachIndexed { index, sunFlowerPage ->
                val title = stringResource(id = sunFlowerPage.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(painter = painterResource(id = sunFlowerPage.drawableResId), contentDescription = title)
                    },
                    unselectedContentColor = MaterialTheme.colors.primaryVariant,
                    selectedContentColor = MaterialTheme.colors.secondary
                )
            }
        }

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) {     index->
            when(pages[index]) {
                SunFlowerPage.MY_GARDEN -> {
                    GardenScreen(
                        Modifier.fillMaxHeight(),
                        onAddPlantClick = { coroutineScope.launch {
                            pagerState.scrollToPage(SunFlowerPage.PLANT_LIST.ordinal)
                        } },
                        onPlantClick = {
                            onPlantClick(it.plant)
                        }
                    )
                }
                SunFlowerPage.PLANT_LIST -> {

                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview(){
    MdcTheme {
        HomePagerScreen(onPlantClick = {}, onPageChange = {})
    }
}