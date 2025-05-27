package com.example.buscaminas.models

// =============================================================================
// VIEWMODEL PARA MANEJAR EL ESTADO DEL JUEGO
// =============================================================================

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class BuscaminasViewModel : ViewModel() {

    private var tableroJuego: TableroJuego? = null
    private var timerJob: Job? = null // Para controlar el Job del timer
    // Estados del juego
    private val _gameState = MutableStateFlow(GameUiState())
    val gameState: StateFlow<GameUiState> = _gameState.asStateFlow()

    private val _tablero = MutableStateFlow<Array<Array<Celda>>?>(null)
    val tablero: StateFlow<Array<Array<Celda>>?> = _tablero.asStateFlow()

    fun iniciarJuego(nivel: NivelDificultad) {
        stopTimer()

        tableroJuego = TableroFactory.crearTablero(nivel).apply {
            configurarListeners(this)
        }
        // --- Punto de verificación crítico ---
        val nuevoTablero = tableroJuego?.obtenerTablero()
        if (nuevoTablero == null) {
            Log.e("BuscaminasViewModel", "ERROR: tableroJuego.obtenerTablero() devolvió null. El tablero no se inicializó correctamente en TableroJuego.")
            return // Salimos si no hay tablero para evitar un NPE.
        }
        if (nuevoTablero.isEmpty() || (nuevoTablero.isNotEmpty() && nuevoTablero[0].isEmpty())) {
            Log.e("BuscaminasViewModel", "ADVERTENCIA: El tablero es vacío o tiene 0 columnas. Filas: ${nuevoTablero.size}, Columnas: ${if (nuevoTablero.isNotEmpty()) nuevoTablero[0].size else 0}")
            return // Salimos si el tablero no tiene dimensiones válidas.
        }
        Log.d("BuscaminasViewModel", "Tablero creado y listo: Filas=${nuevoTablero.size}, Columnas=${nuevoTablero[0].size}")

        _tablero.value = nuevoTablero.deepCopy()
        _gameState.value = GameUiState(
            juegoIniciado = true,
            nivelActual = nivel,
            tiempoInicio = System.currentTimeMillis(),
            minasRestantes = nivel.minas
        )
        startTimer()
    }

    private fun configurarListeners(tablero: TableroJuego) {
        tablero.onGameOver = {
            stopTimer()
            _gameState.value = _gameState.value.copy(
                juegoTerminado = true,
                juegoGanado = false
            )
            _tablero.value = tableroJuego?.obtenerTablero()?.deepCopy()
        }

        tablero.onGameWon = {
            stopTimer()
            _gameState.value = _gameState.value.copy(
                juegoTerminado = true,
                juegoGanado = true
            )
            _tablero.value = tableroJuego?.obtenerTablero()?.deepCopy()
        }

        tablero.onCellRevealed = { celda ->
            // Actualizar el tablero para refrescar la UI
            _tablero.value = tableroJuego?.obtenerTablero()?.deepCopy()
            actualizarEstadisticas()
        }

        tablero.onFlagToggled = { celda ->
            _tablero.value = tableroJuego?.obtenerTablero()?.deepCopy()
            actualizarEstadisticas()
        }
    }

    private fun actualizarEstadisticas() {
        tableroJuego?.let { tablero ->
            val stats = tablero.obtenerEstadisticas()
            _gameState.value = _gameState.value.copy(
                minasRestantes = stats.minasRestantes,
                celdasDestapadas = stats.celdasDestapadas
            )
        }
    }

    fun destaparCelda(fila: Int, columna: Int) {
        viewModelScope.launch {
            tableroJuego?.destaparCelda(fila, columna)
        }
    }

    fun alternarBandera(fila: Int, columna: Int) {
        viewModelScope.launch {
            tableroJuego?.alternarBandera(fila, columna)
        }
        _gameState.value = _gameState.value.copy(
            modoColocarBanderas = !_gameState.value.modoColocarBanderas
        )
    }

    fun reiniciarJuego() {
        tableroJuego?.reiniciarJuego()
        _tablero.value = tableroJuego?.obtenerTablero()?.deepCopy()
        _gameState.value = _gameState.value.copy(
            juegoTerminado = false,
            juegoGanado = false,
            tiempoInicio = System.currentTimeMillis(),
            minasRestantes = _gameState.value.nivelActual.minas,
            celdasDestapadas = 0
        )
    }
    private fun startTimer() {
        // Asegúrate de que solo haya un timerJob activo
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            // Un Flow que emite un valor cada segundo
            flow {
                while (true) {
                    emit(Unit) // Emite un "tick"
                    delay(1000L) // Espera 1 segundo
                }
            }
                .flowOn(Dispatchers.Default) // Ejecuta el delay en un hilo en segundo plano
                .collect {
                    // Solo actualiza si el juego está iniciado y no terminado
                    if (_gameState.value.juegoIniciado && !_gameState.value.juegoTerminado) {
                        val elapsedMillis = System.currentTimeMillis() - _gameState.value.tiempoInicio
                        _gameState.value = _gameState.value.copy(
                            tiempoTranscurrido = elapsedMillis / 1000L // Convertir a segundos
                        )
                    } else {
                        // Si el juego ha terminado o no ha iniciado, cancela el job del timer
                        cancel()
                    }
                }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel() // Cancela el job del timer
        timerJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer() // Asegúrate de detener el timer cuando el ViewModel se destruye
    }
}

// =============================================================================
// ESTADO DE LA UI
// =============================================================================

data class GameUiState(
    val juegoIniciado: Boolean = false,
    val juegoTerminado: Boolean = false,
    val juegoGanado: Boolean = false,
    val nivelActual: NivelDificultad = NivelDificultad.FACIL,
    val tiempoInicio: Long = 0L,
    val minasRestantes: Int = 0,
    val celdasDestapadas: Int = 0,
    val tiempoTranscurrido: Long = 0L, // Para un futuro timer, si lo implementas
    val modoColocarBanderas: Boolean = false // <-- ¡Nueva propiedad!
)

enum class NivelDificultad(
    val filas: Int,
    val columnas: Int,
    val minas: Int,
    val nombre: String
) {
    FACIL(9, 9, 10, "Fácil"),
    MEDIO(16, 16, 40, "Medio"),
    DIFICIL(24, 24, 99, "Difícil");

    companion object {
        fun fromString(nombre: String): NivelDificultad {
            return values().find { it.nombre.equals(nombre, ignoreCase = true) } ?: FACIL
        }
    }
}
fun Array<Array<Celda>>.deepCopy(): Array<Array<Celda>> {
    return Array(this.size) { row ->
        Array(this[row].size) { col ->
            this[row][col].copy() // Usa copy() del data class Celda para clonar la celda
        }
    }
}