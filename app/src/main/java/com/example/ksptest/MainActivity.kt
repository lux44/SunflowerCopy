package com.example.ksptest

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.ksptest.compose.SunflowerApp
import com.example.ksptest.compose.home.SunFlowerPage
import com.example.ksptest.viewmodels.PlantListViewModel
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PlantListViewModel by viewModels()

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_plant_list,menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when(menuItem.itemId) {
                R.id.filter_zone -> {
                    viewModel.updateData()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                setContent {
                    SunflowerApp(
                        onAttached = {toolbar ->
                            setSupportActionBar(toolbar)
                        },
                        onPageChange = {page->
                            when(page) {
                                SunFlowerPage.MY_GARDEN -> removeMenuProvider(menuProvider)
                                SunFlowerPage.PLANT_LIST -> addMenuProvider(menuProvider, this)
                            }
                        }
                    )
                }
            }
        }
    }
}

