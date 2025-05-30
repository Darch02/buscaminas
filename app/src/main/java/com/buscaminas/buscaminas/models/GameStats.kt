package com.buscaminas.buscaminas.models

data class GameStats(
    val celdasDestapadas: Int,
    val totalCeldas: Int,
    val minasRestantes: Int,
    val totalMinas: Int,
    val juegoTerminado: Boolean,
    val juegoGanado: Boolean
)