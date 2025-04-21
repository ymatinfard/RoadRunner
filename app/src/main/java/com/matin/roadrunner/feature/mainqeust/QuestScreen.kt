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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.roadrunner.core.designsystem.RoadRunnerTheme
import com.matin.roadrunner.feature.mainqeust.model.DriverUiModel
import com.matin.roadrunner.feature.mainqeust.model.GroundCellUiModel

@Composable
fun QuestScreen(viewModel: QuestScreenViewModel = hiltViewModel<QuestScreenViewModel>()) {
    val groundCells by viewModel.playGroundCellsState.collectAsStateWithLifecycle()
    QuestScreenContent(cells = groundCells.flatten(), onCellClick = {})
}

@Composable
fun QuestScreenContent(
    cells: List<GroundCellUiModel>,
    onCellClick: (GroundCellUiModel) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val cellModifier =
            Modifier
                .aspectRatio(1f)
                .background(color = MaterialTheme.colorScheme.primary)
                .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary))

        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
        ) {
            items(
                cells,
                key = { cell -> cell.cellId },
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
        modifier =
            modifier
                .clickable { onCellClick(cell) },
    ) {
        when {
            cell.hasRunner -> Text("R")
            cell.driver != null -> Text("D")
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

val fakeGroundCells =
    List(50) { index ->
        GroundCellUiModel(
            driver =
                if (index % 10 == 0) {
                    DriverUiModel("1", "Driver 1", 1, 2, 3)
                } else {
                    null
                },
            cellId = index.toLong(),
        )
    }
