package com.matin.roadrunner.feature.mainqeust

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val runner by viewModel.runnerPosition.collectAsStateWithLifecycle()
    val taxis by viewModel.texis.collectAsStateWithLifecycle()
    val message by viewModel.toastMessage.collectAsStateWithLifecycle(ToastMessageModel())

    NewQuestScreenContent(
        taxis = taxis,
        runner = runner,
        onRestartClick = { viewModel.restartGame() },
        onTaxiClick = { viewModel.onTaxiClick(it) },
    )
}

@Composable
fun NewQuestScreenContent(
    taxis: List<TaxiModel>,
    runner: Position,
    onRestartClick: () -> Unit,
    onTaxiClick: (TaxiModel) -> Unit,
) {
    val gridSize = 10
    val cellSize = 50

    Column {
        Playground(gridSize, cellSize)
        Spacer(Modifier.height(20.dp))
        ControlButton(onRestartClick = onRestartClick)
    }

    RunnerIcon(runner)
    TaxisIcon(taxis, onTaxiClick)
}

@Composable
fun ControlButton(modifier: Modifier = Modifier, onRestartClick: () -> Unit = {}) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = onRestartClick,
        ) {
            Text("Restart")
        }
    }
}

@Composable
private fun Playground(gridSize: Int, cellSize: Int) {
    Log.d("Playground", "Playground")
    Box(
        modifier = Modifier
            .size((gridSize * cellSize).dp)
            .background(Color.LightGray),
    ) {
        Column {
            for (row in 0 until gridSize) {
                Row {
                    for (column in 0 until gridSize) {
                        Box(
                            modifier = Modifier
                                .size(cellSize.dp)
                                .border(1.dp, Color.Gray),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaxisIcon(taxis: List<TaxiModel>, onTaxiClick: (TaxiModel) -> Unit) {
    taxis.forEach { taxi ->
        TaxiIcon(taxi, onTaxiClick = onTaxiClick)
    }
}

@Composable
fun RunnerIcon(runner: Position) {
    val animatedX by animateDpAsState(targetValue = (runner.x * 50).dp)
    val animatedY by animateDpAsState(targetValue = (runner.y * 50).dp)

    Box(
        modifier = Modifier
            .offset(x = animatedX, y = animatedY)
            .size(50.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Runner",
            tint = Color.Green,
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
fun TaxiIcon(taxi: TaxiModel, onTaxiClick: (TaxiModel) -> Unit) {
    val cellSize = 50

    val animatedX by animateDpAsState(targetValue = (taxi.position.x * cellSize).dp)
    val animatedY by animateDpAsState(targetValue = (taxi.position.y * cellSize).dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(x = animatedX, y = animatedY)
            .size(cellSize.dp)
            .clickable { onTaxiClick(taxi) },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_taxi),
            contentDescription = "Taxi",
            tint = Color.Yellow,
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
fun QuestScreenContent(
    cells: List<CellModel>,
    message: ToastMessageModel,
    onCellClick: (CellModel) -> Unit,
    onRestartClick: () -> Unit = {},
) {
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        val cellModifier =
            Modifier
                .aspectRatio(1f)
                .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary))

        Column {
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

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = onRestartClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text("Restart")
            }

            ToastMessage(message, context)
        }
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
