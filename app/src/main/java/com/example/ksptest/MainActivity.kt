package com.example.ksptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.ksptest.compose.SunflowerApp
import com.example.ksptest.compose.home.SunFlowerPage
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
                                SunFlowerPage.MY_GARDEN -> {}
                                SunFlowerPage.PLANT_LIST -> {}
                            }
                        }
                    )
                }
            }
        }
    }
}

