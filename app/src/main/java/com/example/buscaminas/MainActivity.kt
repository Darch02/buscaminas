package com.example.buscaminas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.buscaminas.screens.Home
import com.example.buscaminas.screens.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buscaminas.ui.theme.BuscaminasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuscaminasTheme(dynamicColor = false) {
                val navController = rememberNavController() // 1. Crea el NavController

                NavHost(
                    navController = navController, // 2. Pasa el NavController al NavHost
                    startDestination = "HomeScreen", // 3. Define la pantalla inicial
                ) {
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
                        Statistics(navController = navController) // 4. Pasa el NavController a DifficultyScreen
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