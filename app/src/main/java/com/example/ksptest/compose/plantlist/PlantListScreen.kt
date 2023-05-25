package com.example.ksptest.compose.plantlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ksptest.data.Plant
import com.example.ksptest.viewmodels.PlantListViewModel

@Composable
fun PlantListScreen(
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlantListViewModel = hiltViewModel(),
) {
    val plants by viewModel.plants.observeAsState(initial = emptyList())
    PlantListScreen(plants = plants, modifier, onPlantClick = onPlantClick)
}

@Composable
fun PlantListScreen(
    plants: List<Plant>,
    modifier: Modifier = Modifier,
    onPlantClick: (Plant) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.testTag("plant_list"),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 24.dp
        )
    ) {
        items(
            items = plants,
            key = { it.plantId }
        ) { plant->
            PlantListItem(plant = plant) {
                onPlantClick(plant)
            }
        }
    }
}

@Preview
@Composable
private fun PlantListScreenPreview(
    @PreviewParameter(PlantListPreviewParamProvider::class) plants: List<Plant>
) {
    PlantListScreen(plants)
}

private class PlantListPreviewParamProvider : PreviewParameterProvider<List<Plant>> {
    override val values: Sequence<List<Plant>>
        get() = sequenceOf(
            emptyList(),
            listOf(
                Plant("1", "Apple", "Apple", 1),
                Plant("2", "Banana", "Banana", 2),
                Plant("3", "Carrot", "Carrot", 3),
                Plant("4", "Dill", "Dill", 4),
            )
        )

}