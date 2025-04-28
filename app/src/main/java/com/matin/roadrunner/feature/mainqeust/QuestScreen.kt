package com.matin.roadrunner.feature.mainqeust
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.roadrunner.R
import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.core.common.ToastMessageModel
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel

@Composable
fun QuestScreen(viewModel: QuestScreenViewModel = hiltViewModel<QuestScreenViewModel>()) {
    val runner by viewModel.runnerPosition.collectAsStateWithLifecycle()
    val taxis by viewModel.taxis.collectAsStateWithLifecycle()
    val message by viewModel.toastMessage.collectAsStateWithLifecycle(ToastMessageModel())
    val path by viewModel.path.collectAsStateWithLifecycle()
    val walls by viewModel.walls.collectAsStateWithLifecycle()

    QuestScreenContent(
        taxis = taxis,
        runner = runner,
        path = path,
        walls = walls,
        gameConfig = viewModel.gameConfig,
        onRestartClick = { viewModel.restartGame() },
        onTaxiClick = { viewModel.onTaxiClick(it) },
    )
}

@Composable
fun QuestScreenContent(
    taxis: List<TaxiModel>,
    runner: Position,
    path: List<Position>,
    walls: List<Position>,
    gameConfig: GameConfig,
    onRestartClick: () -> Unit,
    onTaxiClick: (TaxiModel) -> Unit,
) {
    BoxWithConstraints(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        val screeWidth = maxWidth
        val cellCount = gameConfig.cellCount
        val cellSize = screeWidth / cellCount
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier =
                    Modifier
                        .size(cellCount * cellSize)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.medium,
                        )
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium,
                        )
                        .padding(2.dp),
            ) {
                Playground(cellCount, cellSize)
                Path(path, cellSize)
                Walls(walls, cellSize)
                Runner(runner, cellSize)
                Taxis(taxis, onTaxiClick, cellSize)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Path Length: ${path.size}", style = MaterialTheme.typography.labelLarge)
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = onRestartClick,
                shape = MaterialTheme.shapes.medium,
            ) {
                Text("Restart Journey", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun Taxis(taxis: List<TaxiModel>, onTaxiClick: (TaxiModel) -> Unit, cellSize: Dp) {
    taxis.forEach { taxi ->
        TaxiIcon(taxi, onTaxiClick = onTaxiClick, cellSize)
    }
}

@Composable
fun Path(path: List<Position>, cellSize: Dp) {
    path.forEach { position ->
        PathCell(position, cellSize)
    }
}

@Composable
private fun Playground(gridSize: Int, cellSize: Dp) {
    Column {
        for (row in 0 until gridSize) {
            Row {
                for (column in 0 until gridSize) {
                    Box(
                        modifier =
                            Modifier
                                .size(cellSize)
                                .border(
                                    0.5.dp,
                                    MaterialTheme.colorScheme.outlineVariant,
                                    shape = MaterialTheme.shapes.small,
                                ),
                    )
                }
            }
        }
    }
}

@Composable
fun PathCell(position: Position, cellSize: Dp) {
    val animatedX by animateDpAsState(targetValue = position.x * cellSize)
    val animatedY by animateDpAsState(targetValue = position.y * cellSize)

    Box(
        modifier =
            Modifier
                .size(cellSize)
                .offset(x = animatedX, y = animatedY)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    shape = MaterialTheme.shapes.small,
                ),
    )
}

@Composable
fun Runner(runner: Position, cellSize: Dp) {
    val animatedX by animateDpAsState(targetValue = runner.x * cellSize)
    val animatedY by animateDpAsState(targetValue = runner.y * cellSize)

    Box(
        modifier =
            Modifier
                .offset(x = animatedX, y = animatedY)
                .size(cellSize)
                .background(MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.small)
                .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Runner",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
fun TaxiIcon(taxi: TaxiModel, onTaxiClick: (TaxiModel) -> Unit, cellSize: Dp) {
    val animatedX by animateDpAsState(targetValue = taxi.position.x * cellSize)
    val animatedY by animateDpAsState(targetValue = taxi.position.y * cellSize)

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .offset(x = animatedX, y = animatedY)
                .size(cellSize)
                .clickable { onTaxiClick(taxi) }
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.small)
                .border(1.dp, MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.small),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_taxi),
            contentDescription = "Taxi",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
fun Walls(walls: List<Position>, cellSize: Dp) {
    for (wall in walls) {
        Box(
            modifier =
                Modifier
                    .size(cellSize)
                    .offset(x = wall.x * cellSize, y = wall.y * cellSize)
                    .background(
                        MaterialTheme.colorScheme.tertiary.copy(alpha = .7f),
                        shape = MaterialTheme.shapes.small,
                    ),
        )
    }
}
