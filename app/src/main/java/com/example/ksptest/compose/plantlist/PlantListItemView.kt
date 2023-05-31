package com.example.ksptest.compose.plantlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ksptest.R
import com.example.ksptest.compose.card
import com.example.ksptest.compose.util.SunflowerImage
import com.example.ksptest.data.Plant
import com.example.ksptest.data.UnsplashPhoto

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageListItem(name:String, imageUrl: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        elevation = 2.dp,
        shape = MaterialTheme.shapes.card,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 26.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            SunflowerImage(
                model = imageUrl,
                contentDescription = stringResource(id = R.string.a11y_plant_item_image),
                Modifier
                    .fillMaxWidth()
                    .height(95.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun PlantListItem(plant: Plant, onClick: () -> Unit) {
    ImageListItem(name = plant.name, imageUrl = plant.imageUrl, onClick = onClick)
}

@Composable
fun PhotoListItem(photo:UnsplashPhoto, onClick: () -> Unit) {
    ImageListItem(name = photo.user.name, imageUrl = photo.urls.small, onClick = onClick)
}

@Composable
@Preview
fun ItemViewPreview() {
    ImageListItem(name = "Apple", imageUrl = "") {

    }
}

