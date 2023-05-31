package com.example.ksptest.compose.garden

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ksptest.data.PlantAndGardenPlantings
import com.example.ksptest.viewmodels.GardenPlantingListViewModel
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ksptest.R
import com.example.ksptest.compose.card
import com.example.ksptest.compose.util.SunflowerImage
import com.example.ksptest.data.GardenPlanting
import com.example.ksptest.data.Plant
import com.example.ksptest.viewmodels.PlantAndGardenPlantingsViewModel
import com.google.accompanist.themeadapter.material.MdcTheme
import java.util.Calendar

@Composable
fun GardenScreen(
    modifier: Modifier,
    viewModel: GardenPlantingListViewModel = hiltViewModel(),
    onAddPlantClick : () -> Unit,
    onPlantClick: (PlantAndGardenPlantings) -> Unit
) {
    val gardenPlants by viewModel.plantAndGardenPlantings.collectAsState(initial = emptyList())
    GardenScreen(
        gardenPlants = gardenPlants,
        modifier = modifier,
        onAddPlantClick = onAddPlantClick,
        onPlantClick = onPlantClick
    )
}

@Composable
fun GardenScreen(
    gardenPlants: List<PlantAndGardenPlantings>,
    modifier:Modifier = Modifier,
    onAddPlantClick: () -> Unit = {},
    onPlantClick: (PlantAndGardenPlantings) -> Unit = {}
) {
    if (gardenPlants.isEmpty()) {
        EmptyGarden(onAddPlantClick = onAddPlantClick, modifier = modifier)
    } else {
        GardenList(gardenPlants = gardenPlants, onPlantClick = onPlantClick, modifier = modifier)
    }
}

@Composable
private fun EmptyGarden(onAddPlantClick: () -> Unit, modifier: Modifier) {
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(
                bottom = 50.dp
            ),
            text = "Your garden is empty",
            style = MaterialTheme.typography.h5,
        )
        ConstraintLayout(modifier = Modifier) {
            Button(
                modifier = Modifier.constrainAs(this.createRef()) {
                    bottom.linkTo(parent.bottom,300.dp)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 0.dp
                ),
                onClick = onAddPlantClick,
            ) {
                Text(
                    text = stringResource(id = R.string.add_plant),
                    //color = MaterialTheme.colors.primary
                    color = colorResource(id = R.color.sunflower_green_500)
                )
            }
        }

    }
}

@Composable
private fun GardenList(
    gardenPlants: List<PlantAndGardenPlantings>,
    onPlantClick: (PlantAndGardenPlantings) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()

    ReportDrawnWhen {
        gridState.layoutInfo.totalItemsCount > 0
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier,
        state = gridState,
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 12.dp
        )
    ) {
        items(
            items = gardenPlants,
            key = { it.plant.plantId }
        ) {
            GardenListItem(plant = it, onPlantClick = onPlantClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GardenListItem(
    plant: PlantAndGardenPlantings,
    onPlantClick: (PlantAndGardenPlantings) -> Unit
) {
   val viewModel = PlantAndGardenPlantingsViewModel(plant)

   val cardSideMargin = 12.dp
   val marginNormal = 16.dp

    Card(
        onClick = { onPlantClick(plant) },
        modifier = Modifier.padding(
            start = cardSideMargin,
            end = cardSideMargin,
            bottom = 26.dp
        ),
        elevation = 2.dp,
        shape = MaterialTheme.shapes.card,
    ) {
        Column(Modifier.fillMaxWidth()) {
            SunflowerImage(
                model = viewModel.imageUrl,
                contentDescription = plant.plant.description,
                Modifier
                    .fillMaxWidth()
                    .height(95.dp),
                contentScale = ContentScale.Crop
            )

            // Plant name
            Text(
                text = viewModel.plantName,
                Modifier
                    .padding(vertical = marginNormal)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle1,
            )

            // Plant Date
            Text(
                text = "Planted",
                Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.sunflower_green_700),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = viewModel.plantDateString,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle2
            )

            // Last Watered
            Text(
                text = stringResource(id = R.string.watered_date_header),
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = marginNormal),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.sunflower_green_700),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = viewModel.waterDateString,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = pluralStringResource(
                id = R.plurals.watering_next,
                count = viewModel.wateringInterval,
                viewModel.wateringInterval
                ),
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = marginNormal),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Preview
@Composable
private fun GardenScreenPreview(
    @PreviewParameter(GardenScreenPreviewParamProvider::class) gardenPlants: List<PlantAndGardenPlantings>
) {
    MdcTheme {
        GardenScreen(gardenPlants = gardenPlants)
    }
}

@Preview
@Composable
private fun GardenScreenItemPreview(
    @PreviewParameter(GardenScreenPreviewParamProvider::class) gardenPlants: List<PlantAndGardenPlantings>
) {
    MdcTheme {
        GardenScreen(gardenPlants = gardenPlants)
    }
}

private class GardenScreenPreviewParamProvider :
        PreviewParameterProvider<List<PlantAndGardenPlantings>> {
    override val values: Sequence<List<PlantAndGardenPlantings>>
        get() = sequenceOf(
            emptyList(),
            listOf(
                PlantAndGardenPlantings(
                    plant = Plant(
                        plantId = "1",
                        name = "Apple",
                        description = "An apple",
                        growZoneNumber = 1,
                        wateringInterval = 2,
                        imageUrl = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=400&fit=max"
                    ),
                    gardenPlantings = listOf(
                        GardenPlanting(
                            plantId = "1",
                            plantDate = Calendar.getInstance(),
                            lastWateringDate = Calendar.getInstance()
                        )
                    )
                )
            )
        )

}

//@Preview
//@Composable
//private fun GardenScreenItemViewPreview(
//    @PreviewParameter(GardenScreenItemPreviewParamProvider::class) plant:Plant
//) {
//    MdcTheme {
//        GardenListItem(plant = plant, onPlantClick = {})
//    }
//}
//
//private class GardenScreenItemPreviewParamProvider: PreviewParameterProvider<Plant> {
//    override val values: Sequence<Plant>
//        get() = sequence {
//            Plant(
//                plantId = "1",
//                name = "Apple",
//                description = "An Apple",
//                growZoneNumber = 1,
//                wateringInterval = 2,
//                imageUrl = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=400&fit=max"
//            )
//        }
//}

