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
                    delay(2500) // Muestra la Splash Screen durante 2 segundos (ajusta a tu gusto)
                    showSplashScreen = false // Desactiva la Splash Screen
                }

                NavHost(
                    navController = navController, // 2. Pasa el NavController al NavHost
                    startDestination = if (showSplashScreen) "splash_screen" else "HomeScreen", // Inicia en Splash o Home
                    modifier = Modifier.fillMaxSize() // 3. Define la pantalla inicial
                ) {
                    composable("splash_screen") {
                        OnBoarding()
                    }
                    composable("HomeScreen") {
                        Home(navController = navController) // 4. Pasa el NavController a HomeScreen
                    }
                    composable("DifficultyScreen") {
                        Difficulty(navController = navController) // 4. Pasa el NavController a DifficultyScreen
                    }
                    composable("CreditsScreen") {
                        Credits(navController = navController) // 4. Pasa el NavController a DifficultyScreen
                    }
                    composable("StatisticsScreen") {
                        val context = LocalContext.current
                        val statistics = remember { StatisticsData(context) }
                        Statistics(navController = navController, statisticsData = statistics) // 4. Pasa el NavController a DifficultyScreen
                    }
                    composable("GameScreen/{nivel}", arguments = listOf(navArgument("nivel") { type = NavType.StringType })) {
                        val nivelString = it.arguments?.getString("nivel")
                        val nivel = NivelDificultad.fromString(nivelString ?: "FACIL")
                        val viewModel: BuscaminasViewModel = viewModel()
                        BuscaminasGameScreen(navController = navController, viewModel = viewModel, nivel = nivel) // 4. Pasa el NavController a DifficultyScreen
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