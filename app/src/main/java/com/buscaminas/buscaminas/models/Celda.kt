package com.buscaminas.buscaminas.models

// =============================================================================
// MODELO DE DATOS - CLASE CELDA
// =============================================================================

data class Celda(
    val fila: Int,
    val columna: Int,
    var tieneMina: Boolean = false,
    var destapada: Boolean = false,
    var tieneBandera: Boolean = false,
    var minasAlrededor: Int = 0
) {

    /**
     * Método para destapar la celda
     * Retorna true si la celda contiene una mina (game over)
     */
    fun destapar(): Boolean {
        if (destapada || tieneBandera) return false

        destapada = true
        return tieneMina
    }

    /**
     * Método para alternar el estado de la bandera
     */
    fun alternarBandera(): Boolean {
        if (!destapada) {
            tieneBandera = !tieneBandera
            return true
        }
        return false
    }

    /**
     * Verifica si la celda puede ser destapada
     */
    fun puedeDestaparse(): Boolean = !destapada && !tieneBandera
}




