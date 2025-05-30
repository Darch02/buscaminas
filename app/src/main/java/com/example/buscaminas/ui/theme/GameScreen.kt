package com.example.buscaminas.ui.theme

// =============================================================================
// COMPOSABLES PARA LA UI
// =============================================================================

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buscaminas.R
import com.example.buscaminas.models.*
import com.example.buscaminas.models.Celda
import com.example.buscaminas.models.GameStats
import com.example.buscaminas.screens.ExplosionAnimation
import com.example.buscaminas.screens.PauseButton
import com.example.buscaminas.screens.PauseScreen

@Composable
fun BuscaminasGameScreen(
    viewModel: BuscaminasViewModel,
    nivel: NivelDificultad = NivelDificultad.FACIL,
    navController: NavController? = null
) {
    val gameState by viewModel.gameState.collectAsState()
    val tablero by viewModel.tablero.collectAsState()
    var showPauseScreen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!gameState.juegoIniciado) {
            viewModel.iniciarJuego(nivel)
        }
    }

    LaunchedEffect(gameState.juegoTerminado, !gameState.animacionGameOverActiva) {
        Log.e("BuscaminasGameScreen", "juegoTerminado: ${gameState.juegoTerminado}")
        Log.e("BuscaminasGameScreen", "juegoGanado: ${gameState.juegoGanado}")
        Log.e("BuscaminasGameScreen", "animacionGameOverActiva: ${gameState.animacionGameOverActiva}")
        if (gameState.juegoTerminado && !gameState.juegoGanado ) {
            if(!gameState.animacionGameOverActiva){
                val stats = viewModel.obtenerGameStats()
                stats?.let {
                    navController?.navigate("LoseScreen/${it.celdasDestapadas}/${it.totalCeldas}/${it.minasRestantes}/${it.totalMinas}")
                }
            }
        } else if (gameState.juegoTerminado && gameState.juegoGanado) {
            val stats = viewModel.obtenerGameStats()
            stats?.let {
                navController?.navigate("WinScreen/${it.celdasDestapadas}/${it.totalCeldas}/${it.minasRestantes}/${it.totalMinas}")
            }
        }
    }

    if (showPauseScreen) {
        PauseScreen(
            navController = navController,
            onResume = { showPauseScreen = false }
        )
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                // Barra superior con temporizador y botón de pausa
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Temporizador
                    Text(
                        text = formatTime(gameState.tiempoTranscurrido),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Botón de pausa
                    PauseButton(
                        onClick = { showPauseScreen = true }
                    )
                }

                // Barra de información del juego
                GameInfoBar(
                    gameState = gameState
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tablero del juego
                tablero?.let { board ->
                    GameBoard(
                        tablero = board,
                        gameState = gameState,
                        onCellClick = { fila, columna ->
                            viewModel.destaparCelda(fila, columna)
                        },
                        onCellLongClick = { fila, columna ->
                            viewModel.alternarBandera(fila, columna)
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun GameInfoBar(
    gameState: GameUiState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.LightGray,
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Contador de minas
        Text(
            text = String.format("%03d", gameState.minasRestantes),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

private fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameBoard(
    tablero: Array<Array<Celda>>,
    gameState: GameUiState,
    onCellClick: (Int, Int) -> Unit,
    onCellLongClick: (Int, Int) -> Unit,
    viewModel: BuscaminasViewModel
) {
    val filas = tablero.size
    val columnas = if (filas > 0) tablero[0].size else 0

    if (filas == 0 || columnas == 0) {
        Text("Error: Tablero con dimensiones cero", color = Color.Red)
        return // No intentes renderizar si las dimensiones son inválidas
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnas),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .fillMaxWidth() // Esto es crucial: el grid ocupa todo el ancho disponible
            .aspectRatio(columnas.toFloat() / filas) // Opcional: para que el tablero sea cuadrado en general
        // Si el número de filas y columnas es diferente, ajusta este aspectRatio
        // Si no quieres que el tablero sea perfectamente cuadrado, puedes quitarlo
    ) {
        items(filas * columnas) { index ->
            val fila = index / columnas
            val columna = index % columnas
            val celda = tablero[fila][columna]
            val minasParaAnimar by viewModel.minasParaAnimar.collectAsState()
            val indiceMinaAnimandose by viewModel.indiceMinaAnimandose.collectAsState()

            CeldaView(
                celda = celda,
                onClick = { onCellClick(fila, columna) },
                onLongClick = { onCellLongClick(fila, columna) },
                enabled = !gameState.juegoTerminado,
                animacionGameOverActiva = gameState.animacionGameOverActiva,
                minasParaAnimar = minasParaAnimar,
                indiceMinaAnimandose = indiceMinaAnimandose,
                fila = fila,
                columna = columna
            )
        }
    }
}

// En CeldaView
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CeldaView(
    celda: Celda,
    // REMUEVE 'size: androidx.compose.ui.unit.Dp,'
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    enabled: Boolean,
    animacionGameOverActiva: Boolean,
    minasParaAnimar: List<Pair<Int, Int>>,
    indiceMinaAnimandose: Int,
    fila: Int,
    columna: Int,
) {
    val backgroundColor = when {
        !celda.destapada -> Color(0xFFBDBDBD) // Gris para celdas sin destapar
        celda.tieneMina -> Color(0xFFFF5252) // Rojo para minas
        else -> Color(0xFFE0E0E0) // Gris claro para celdas destapadas
    }

    val borderColor = when {
        !celda.destapada -> Color(0xFF757575)
        else -> Color(0xFFBDBDBD)
    }

    Box(
        modifier = Modifier
            // .size(size) // REMUEVE ESTA LÍNEA
            .fillMaxWidth() // Ocupa el ancho disponible en su celda de la cuadrícula
            .aspectRatio(1f) // Esto hace que la celda sea cuadrada
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .combinedClickable(
                enabled = enabled,
                onClick = onClick,
                onLongClick = onLongClick
            ),
        contentAlignment = Alignment.Center
    ) {


        val iconSizeModifier = Modifier.fillMaxSize(0.6f) // 60% del tamaño del Box
        val esMinaAAnimar = minasParaAnimar.getOrNull(indiceMinaAnimandose) == Pair(fila, columna)
        when {
            celda.tieneBandera -> {
                Icon(
                    painter = painterResource(id = R.drawable.flag),
                    contentDescription = "Bandera",
                    tint = Color.Black,
                    // Usa el nuevo modificador de tamaño
                    modifier = iconSizeModifier
                )
            }

            celda.destapada && celda.tieneMina -> {
                if (animacionGameOverActiva && esMinaAAnimar) {
                    ExplosionAnimation() // Muestra la animación si está activa y es una mina no destapada
                } else {
                    Text(
                        text = "💣",
                        fontSize = 24.sp
                    )
                }
            }

            celda.destapada && celda.minasAlrededor > 0 -> {
                Text(
                    text = celda.minasAlrededor.toString(),
                    // Puedes usar fontSize directamente
                    fontSize = 20.sp, // Ajusta este valor
                    fontWeight = FontWeight.Bold,
                    color = getNumberColor(celda.minasAlrededor),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun getNumberColor(numero: Int): Color {
    return when (numero) {
        1 -> Color(0xFF1976D2) // Azul
        2 -> Color(0xFF388E3C) // Verde
        3 -> Color(0xFFD32F2F) // Rojo
        4 -> Color(0xFF7B1FA2) // Púrpura
        5 -> Color(0xFF689F38) // Verde oliva
        6 -> Color(0xFFE64A19) // Naranja rojizo
        7 -> Color(0xFF000000) // Negro
        8 -> Color(0xFF424242) // Gris oscuro
        else -> Color.Black
    }
}

@Preview
@Composable
fun BuscaminasGameScreenPreview() {
    val viewModel: BuscaminasViewModel = viewModel()
    BuscaminasTheme(dynamicColor = false) {
        BuscaminasGameScreen(viewModel)
    }
}