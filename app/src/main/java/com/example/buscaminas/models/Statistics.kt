package com.example.buscaminas.models
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class StatisticsData(private val context: Context) {

    // 1. Definir las claves para tus preferencias
    // Puedes tener diferentes tipos de datos:
    private object PreferencesKeys {
        val PARTIDAS = intPreferencesKey("partidas")
        val VICTORIAS = intPreferencesKey("victorias")
        val RECORD = longPreferencesKey("record")
        val PARTIDAS_MEDIO = intPreferencesKey("partidas_medio")
        val VICTORIAS_MEDIO = intPreferencesKey("victorias_medio")
        val RECORD_MEDIO = longPreferencesKey("record_medio")
        val PARTIDAS_DIFICIL = intPreferencesKey("partidas_dificl")
        val VICTORIAS_DIFICIL = intPreferencesKey("victorias_dificl")
        val RECORD_DIFICIL = longPreferencesKey("record_dificl")
    }

    // 2. Métodos para leer las preferencias (retornan un Flow)
    val partidas: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // Si la clave no existe, retornará null
            preferences[PreferencesKeys.PARTIDAS] ?: 0
        }
    val partidas_medio: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // Si la clave no existe, retornará null
            preferences[PreferencesKeys.PARTIDAS_MEDIO] ?: 0
        }
    val partidas_dificil: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // Si la clave no existe, retornará null
            preferences[PreferencesKeys.PARTIDAS_DIFICIL] ?: 0
        }

    val victorias: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // Puedes proporcionar un valor por defecto si la clave no existe
            preferences[PreferencesKeys.VICTORIAS] ?: 0
        }
    val victorias_medio: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // Puedes proporcionar un valor por defecto si la clave no existe
            preferences[PreferencesKeys.VICTORIAS_MEDIO] ?: 0
        }
    val victorias_dificil: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // Puedes proporcionar un valor por defecto si la clave no existe
            preferences[PreferencesKeys.VICTORIAS_DIFICIL] ?: 0
        }

    val record: Flow<Long> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.RECORD] ?: 0
        }
    val record_medio: Flow<Long> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.RECORD_MEDIO] ?: 0
        }
    val record_dificil: Flow<Long> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.RECORD_DIFICIL] ?: 0
        }

    // 3. Métodos para escribir las preferencias (suspending functions)
    suspend fun addPartida(nivel: String) {
        context.dataStore.edit { preferences ->
            if (nivel == "Fácil") {
                preferences[PreferencesKeys.PARTIDAS] = (preferences[PreferencesKeys.PARTIDAS] ?: 0) + 1
            }else if (nivel == "Medio") {
                preferences[PreferencesKeys.PARTIDAS_MEDIO] = (preferences[PreferencesKeys.PARTIDAS_MEDIO] ?: 0) + 1
            }else if (nivel == "Difícil") {
                preferences[PreferencesKeys.PARTIDAS_DIFICIL] = (preferences[PreferencesKeys.PARTIDAS_DIFICIL] ?: 0) + 1
            }

        }
    }

    suspend fun addVictoria(nivel: String) {
        context.dataStore.edit { preferences ->
            if (nivel == "Fácil") {
                preferences[PreferencesKeys.VICTORIAS] = (preferences[PreferencesKeys.VICTORIAS] ?: 0) + 1
            }else if (nivel == "Medio") {
                preferences[PreferencesKeys.VICTORIAS_MEDIO] = (preferences[PreferencesKeys.VICTORIAS_MEDIO] ?: 0) + 1
            }else if (nivel == "Difícil") {
                preferences[PreferencesKeys.VICTORIAS_DIFICIL] = (preferences[PreferencesKeys.VICTORIAS_DIFICIL] ?: 0) + 1
            }
        }
    }

    suspend fun addRecord(time : Long, nivel: String) {
        context.dataStore.edit { preferences ->
            if (nivel == "Fácil") {
                if(time > (preferences[PreferencesKeys.RECORD] ?: 0)){
                    preferences[PreferencesKeys.RECORD] = time
                }
            }else if (nivel == "Medio") {
                if(time > (preferences[PreferencesKeys.RECORD_MEDIO] ?: 0)){
                    preferences[PreferencesKeys.RECORD_MEDIO] = time
                }
            }else if (nivel == "Difícil") {
                if(time > (preferences[PreferencesKeys.RECORD_DIFICIL] ?: 0)){
                    preferences[PreferencesKeys.RECORD_DIFICIL] = time
                }
            }
        }
    }

}