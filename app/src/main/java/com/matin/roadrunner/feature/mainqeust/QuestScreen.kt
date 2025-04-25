package com.matin.roadrunner.feature.mainqeust

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.roadrunner.R
import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.core.common.ToastMessageModel
import com.matin.roadrunner.core.designsystem.RoadRunnerTheme
import com.matin.roadrunner.core.designsystem.ToastMessage
import com.matin.roadrunner.feature.mainqeust.model.CellModel
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel

@Composable
fun QuestScreen(viewModel: QuestScreenViewModel = hiltViewModel<QuestScreenViewModel>()) {
    val groundCells by viewModel.playGroundCellsState.collectAsStateWithLifecycle()
    val message by viewModel.toastMessage.collectAsStateWithLifecycle(ToastMessageModel())
    QuestScreenContent(
        cells = groundCells.flatten(),
        message,
        onCellClick = {
            viewModel.onCellClick(
                it,
            )
        },
    )
}

@Composable
fun QuestScreenContent(
    cells: List<CellModel>,
    message: ToastMessageModel,
    onCellClick: (CellModel) -> Unit,
) {
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        val cellModifier =
            Modifier
                .aspectRatio(1f)
                .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary))

        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
        ) {
            items(
                cells,
                key = { cell -> cell.cellId },
            ) { cell ->
                PlaygroundCell(cellModifier, cell, onCellClick)
            }
        }
        ToastMessage(message, context)
    }
}

@Composable
fun PlaygroundCell(
    modifier: Modifier,
    cell: CellModel,
    onCellClick: (CellModel) -> Unit,
) {
    Box(
        modifier =
            modifier
                .clickable { onCellClick(cell) },
        contentAlignment = Alignment.Center,
    ) {
        when {
            cell.hasRunner -> CellImage(resId = R.drawable.ic_person, "Runner")
            cell.taxi != null -> CellImage(resId = R.drawable.ic_taxi, "Taxi")
            else -> Text(cell.cellId.toString())
        }
    }
}

@Composable
private fun CellImage(resId: Int, contentDescription: String) {
    Image(
        painter = painterResource(id = resId),
        modifier = Modifier.size(24.dp),
        contentDescription = contentDescription,
    )
}

@Preview(showBackground = true)
@Composable
fun QuestScreenPreview() {
    RoadRunnerTheme {
        QuestScreenContent(fakeGroundCells, ToastMessageModel(), {})
    }
}

val fakeGroundCells =
    List(100) { index ->
        CellModel(
            hasRunner = index == 55,
            taxi =
                if (index % 10 == 0) {
                    TaxiModel("1", "Taxi 1", 1, position = Position(3, 2))
                } else {
                    null
                },
            cellId = index.toLong(),
        )
    }
