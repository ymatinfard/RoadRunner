package com.matin.roadrunner.feature.mainqeust

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.roadrunner.core.designsystem.RoadRunnerTheme
import com.matin.roadrunner.feature.mainqeust.model.DriverUiModel
import com.matin.roadrunner.feature.mainqeust.model.GroundCellUiModel

@Composable
fun QuestScreen() {

}

@Composable
fun QuestScreenContent(
    groundCells: List<GroundCellUiModel>,
    onCellClick: (GroundCellUiModel) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val cellModifier = Modifier
            .aspectRatio(1f)
            .background(color = MaterialTheme.colorScheme.primary)
            .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary))

        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
        ) {
            items(
                groundCells,
                key = { it.cellId },
            ) { cell ->
                GroundCell(cellModifier, cell, onCellClick)
            }
        }
    }
}

@Composable
fun GroundCell(
    modifier: Modifier,
    cell: GroundCellUiModel,
    onCellClick: (GroundCellUiModel) -> Unit,
) {
    Box(
        modifier = modifier
            .clickable { onCellClick(cell) },

        ) {
        if (cell.hasRunner) {
            Text(text = "R")
        } else if (cell.driver != null) {
            Text(text = "D")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestScreenPreview() {
    RoadRunnerTheme {
        QuestScreenContent(fakeGroundCells, {})
    }
}

val fakeGroundCells = List(50) { index ->
    GroundCellUiModel(
        driver = if (index % 10 == 0) {
            DriverUiModel("1", "Driver 1", 5)
        } else {
            null
        },
        cellId = index.toLong(),
    )
}