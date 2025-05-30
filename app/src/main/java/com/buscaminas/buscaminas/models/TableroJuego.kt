package com.buscaminas.buscaminas.models

import android.util.Log

// =============================================================================
// CLASE SUPERIOR - TABLERO DEL JUEGO
// =============================================================================

class TableroJuego(
    private val filas: Int,
    private val columnas: Int,
    private val numeroMinas: Int
) {

    private var tablero: Array<Array<Celda>> = Array(filas) { fila ->
        Array(columnas) { columna ->
            Celda(fila, columna)
        }
    }

    private var juegoTerminado = false
    private var juegoGanado = false
    private var celdasDestapadas = 0
    private val totalCeldasSinMinas = filas * columnas - numeroMinas
    private var minasRestantes = numeroMinas

    // Listeners para eventos del juego
    var onGameOver: (() -> Unit)? = null
    var onGameWon: (() -> Unit)? = null
    var onCellRevealed: ((Celda) -> Unit)? = null
    var onFlagToggled: ((Celda) -> Unit)? = null

    init {
        // Añade un log aquí para confirmar que el init block se ejecuta
        Log.d("TableroJuego", "Inicializando TableroJuego con $filas filas, $columnas columnas, $numeroMinas minas.")
        inicializarTablero()
        // Después de inicializar, verifica las dimensiones del tablero
        Log.d("TableroJuego", "Tablero interno después de inicializar: ${tablero.size} filas, ${tablero[0].size} columnas.")
    }

    /**
     * Inicializa el tablero con minas aleatorias y calcula números
     */
    private fun inicializarTablero() {
        colocarMinasAleatoriamente()
        calcularNumerosAlrededor()
    }

    /**
     * Coloca las minas aleatoriamente en el tablero
     */
    private fun colocarMinasAleatoriamente() {
        val posicionesDisponibles = mutableListOf<Pair<Int, Int>>()

        // Crear lista de todas las posiciones posibles
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                posicionesDisponibles.add(Pair(i, j))
            }
        }

        // Mezclar y tomar las primeras 'numeroMinas' posiciones
        posicionesDisponibles.shuffle()

        for (i in 0 until numeroMinas) {
            val (fila, columna) = posicionesDisponibles[i]
            tablero[fila][columna].tieneMina = true
        }
    }

    /**
     * Calcula el número de minas alrededor para cada celda
     */
    private fun calcularNumerosAlrededor() {
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                if (!tablero[i][j].tieneMina) {
                    tablero[i][j].minasAlrededor = contarMinasAlrededor(i, j)
                }
            }
        }
    }

    /**
     * Cuenta las minas alrededor de una celda específica
     */
    private fun contarMinasAlrededor(fila: Int, columna: Int): Int {
        var contador = 0

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue // Saltar la celda central

                val nuevaFila = fila + i
                val nuevaColumna = columna + j

                if (esposicionValida(nuevaFila, nuevaColumna) &&
                    tablero[nuevaFila][nuevaColumna].tieneMina) {
                    contador++
                }
            }
        }

        return contador
    }

    /**
     * Verifica si una posición está dentro de los límites del tablero
     */
    private fun esposicionValida(fila: Int, columna: Int): Boolean {
        return fila in 0 until filas && columna in 0 until columnas
    }

    /**
     * Obtiene las celdas vecinas de una celda específica
     */
    private fun obtenerCeldasVecinas(fila: Int, columna: Int): List<Celda> {
        val vecinas = mutableListOf<Celda>()

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue

                val nuevaFila = fila + i
                val nuevaColumna = columna + j

                if (esposicionValida(nuevaFila, nuevaColumna)) {
                    vecinas.add(tablero[nuevaFila][nuevaColumna])
                }
            }
        }

        return vecinas
    }

    /**
     * Destapa una celda y maneja la lógica del juego
     */
    fun destaparCelda(fila: Int, columna: Int): Boolean {
        if (juegoTerminado || !esposicionValida(fila, columna)) return false

        val celda = tablero[fila][columna]

        if (!celda.puedeDestaparse()) return false

        val esMina = tablero[fila][columna].destapar()

        if (esMina) {
            // Game Over - destapar todas las minas
            juegoTerminado = true
            destaparTodasLasMinas()
            onGameOver?.invoke()
            return true
        } else {
            celdasDestapadas++
            onCellRevealed?.invoke(celda)

            // Si la celda no tiene minas alrededor, destapar automáticamente las vecinas
            if (celda.minasAlrededor == 0) {
                destaparCeldasVaciasRecursivamente(fila, columna)
            }

            // Verificar si se ganó el juego
            if (celdasDestapadas == totalCeldasSinMinas && minasRestantes == 0) {
                juegoTerminado = true
                juegoGanado = true
                onGameWon?.invoke()
            }

            return false
        }
    }

    /**
     * Destapa recursivamente las celdas vacías adyacentes
     */
    private fun destaparCeldasVaciasRecursivamente(fila: Int, columna: Int) {
        val celdasVecinas = obtenerCeldasVecinas(fila, columna)

        for (vecina in celdasVecinas) {
            if (vecina.puedeDestaparse()) {
                vecina.destapar()
                celdasDestapadas++
                onCellRevealed?.invoke(vecina)

                // Si la celda vecina también está vacía, continuar recursivamente
                if (vecina.minasAlrededor == 0) {
                    destaparCeldasVaciasRecursivamente(vecina.fila, vecina.columna)
                }
            }
        }
    }

    /**
     * Destapa todas las minas (usado cuando se pierde el juego)
     */
    private fun destaparTodasLasMinas() {
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                if (tablero[i][j].tieneMina) {
                    tablero[i][j].destapada = true
                    onCellRevealed?.invoke(tablero[i][j])
                }
            }
        }
    }

    /**
     * Alterna la bandera en una celda específica
     */
    fun alternarBandera(fila: Int, columna: Int): Boolean {
        if (juegoTerminado || !esposicionValida(fila, columna)) return false

        val celda = tablero[fila][columna]
        val resultado = celda.alternarBandera()
        if(celda.tieneMina){
            minasRestantes--
        }

        if (resultado) {
            onFlagToggled?.invoke(celda)
        }

        return resultado
    }

    /**
     * Obtiene una celda específica del tablero
     */
    fun obtenerCelda(fila: Int, columna: Int): Celda? {
        return if (esposicionValida(fila, columna)) {
            tablero[fila][columna]
        } else null
    }

    /**
     * Obtiene el tablero completo
     */
    fun obtenerTablero(): Array<Array<Celda>> = tablero

    /**
     * Reinicia el juego
     */
    fun reiniciarJuego() {
        juegoTerminado = false
        juegoGanado = false
        celdasDestapadas = 0

        // Reinicializar todas las celdas
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                tablero[i][j] = Celda(i, j)
            }
        }

        inicializarTablero()
    }


    /**
     * Obtiene estadísticas del juego
     */
    fun obtenerEstadisticas(): GameStats {
        return GameStats(
            celdasDestapadas = celdasDestapadas,
            totalCeldas = filas * columnas,
            minasRestantes = minasRestantes,
            totalMinas = numeroMinas,
            juegoTerminado = juegoTerminado,
            juegoGanado = juegoGanado
        )
    }
}

// =============================================================================
// FACTORY PARA CREAR TABLEROS SEGÚN DIFICULTAD
// =============================================================================

object TableroFactory {
    fun crearTablero(nivel: NivelDificultad): TableroJuego {
        return TableroJuego(nivel.filas, nivel.columnas, nivel.minas)
    }
}