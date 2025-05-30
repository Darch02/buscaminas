package com.example.buscaminas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import com.example.buscaminas.screens.Home
import com.example.buscaminas.screens.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.buscaminas.models.BuscaminasViewModel
import com.example.buscaminas.models.GameStats
import com.example.buscaminas.models.NivelDificultad
import com.example.buscaminas.models.StatisticsData
import com.example.buscaminas.ui.theme.BuscaminasGameScreen
import com.example.buscaminas.ui.theme.BuscaminasTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuscaminasTheme(dynamicColor = false) {
                val navController = rememberNavController() // 1. Crea el NavController
                var showSplashScreen by remember { mutableStateOf(true) }

                // LaunchedEffect para manejar el tiempo de la Splash Screen
                LaunchedEffect(key1 = true) { // key1 = true asegura que se ejecuta solo una vez
                    delay(1000) // Muestra la Splash Screen durante 1 segundo (reducido de 2.5 segundos)
                    showSplashScreen = false // Desactiva la Splash Screen
                }

                if (showSplashScreen) {
                    // Muestra la Splash Screen mientras showSplashScreen es true
                    OnBoarding()
                } else {
                    // Una vez que la Splash Screen termina, muestra la navegación principal
                    NavHost(
                        navController = navController, // 2. Pasa el NavController al NavHost
                        startDestination = "HomeScreen", // 3. Define la pantalla inicial después del splash
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("HomeScreen") {
                            Home(navController = navController) // 4. Pasa el NavController a HomeScreen
                        }
                        composable("DifficultyScreen") {
                            Difficulty(navController = navController) // 4. Pasa el NavController a DifficultyScreen
                        }
                        composable("CreditsScreen") {
                            Credits(navController = navController) // 4. Pasa el NavController a CreditsScreen
                        }
                        composable("StatisticsScreen") {
                            val context = LocalContext.current
                            val statistics = remember { StatisticsData(context) }
                            Statistics(navController = navController, statisticsData = statistics) // 4. Pasa el NavController a StatisticsScreen
                        }
                        composable("GameScreen/{nivel}", arguments = listOf(navArgument("nivel") { type = NavType.StringType })) {
                            val nivelString = it.arguments?.getString("nivel")
                            val nivel = NivelDificultad.fromString(nivelString ?: "FACIL")
                            val viewModel: BuscaminasViewModel = viewModel()
                            BuscaminasGameScreen(navController = navController, viewModel = viewModel, nivel = nivel) // 4. Pasa el NavController a GameScreen
                        }
                        composable("LoseScreen/{celdasDestapadas}/{totalCeldas}/{minasRestantes}/{totalMinas}", arguments = listOf(
                            navArgument("celdasDestapadas") { type = NavType.IntType },
                            navArgument("totalCeldas") { type = NavType.IntType },
                            navArgument("minasRestantes") { type = NavType.IntType },
                            navArgument("totalMinas") { type = NavType.IntType }
                        )) {
                            val celdasDestapadas = it.arguments?.getInt("celdasDestapadas") ?: 0
                            val totalCeldas = it.arguments?.getInt("totalCeldas") ?: 0
                            val minasRestantes = it.arguments?.getInt("minasRestantes") ?: 0
                            val totalMinas = it.arguments?.getInt("totalMinas") ?: 0
                            val gameStats = GameStats(celdasDestapadas, totalCeldas, minasRestantes, totalMinas, true, false)
                            LoseScreen(gameStats = gameStats, navController = navController)
                        }
                        composable("WinScreen/{celdasDestapadas}/{totalCeldas}/{minasRestantes}/{totalMinas}", arguments = listOf(
                            navArgument("celdasDestapadas") { type = NavType.IntType },
                            navArgument("totalCeldas") { type = NavType.IntType },
                            navArgument("minasRestantes") { type = NavType.IntType },
                            navArgument("totalMinas") { type = NavType.IntType }
                        )) {
                            val celdasDestapadas = it.arguments?.getInt("celdasDestapadas") ?: 0
                            val totalCeldas = it.arguments?.getInt("totalCeldas") ?: 0
                            val minasRestantes = it.arguments?.getInt("minasRestantes") ?: 0
                            val totalMinas = it.arguments?.getInt("totalMinas") ?: 0
                            val gameStats = GameStats(celdasDestapadas, totalCeldas, minasRestantes, totalMinas, true, true)
                            WinScreen(gameStats = gameStats, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuscaminasTheme {
        Greeting("Android")
    }
}