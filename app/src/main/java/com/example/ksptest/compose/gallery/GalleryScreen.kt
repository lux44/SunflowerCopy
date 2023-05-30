package com.example.ksptest.compose.gallery

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ksptest.data.UnsplashPhoto
import com.example.ksptest.viewmodels.GalleryViewModel

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel = hiltViewModel(),
    onPhotoClick: (UnsplashPhoto) -> Unit,
    onUpClick: () -> Unit
) {

}