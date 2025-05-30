package com.buscaminas.buscaminas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.buscaminas.buscaminas.R
import com.buscaminas.buscaminas.ui.theme.BuscaminasTheme

@Composable
fun Difficulty(
    modifier: Modifier = Modifier,
    navController: NavController? = null
){
    Scaffold(
        modifier = modifier
    ){
            innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(MaterialTheme.colorScheme.primaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier.padding(innerPadding).size(150.dp)
            )
            Text(
                text = "Dificultad",
                modifier.padding(innerPadding).padding(bottom = 50.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Boton2(
                levelName = "Fácil",
                levelDescription = "9x9 - 10 minas",
                onClick = { navController?.navigate("GameScreen/Fácil") }
            )
            Boton2(
                levelName = "Medio",
                levelDescription = "9x15 - 17 minas",
                onClick = { navController?.navigate("GameScreen/Medio") }
            )
            Boton2(
                levelName = "Difícil",
                levelDescription = "10x20 - 25 minas",
                onClick = { navController?.navigate("GameScreen/Difícil") }
            )

            Spacer(modifier = Modifier.weight(1f))

            Boton1(
                modifier = Modifier
                    .padding(horizontal = 16.dp) // Padding horizontal para que no toque los bordes
                    .padding(bottom = 30.dp),    // Padding para separarlo del borde inferior
                botonText = "Volver",
                onClick = { navController?.navigate("HomeScreen") }
            )

        }


    }
}

@Preview(showBackground = true)
@Composable
fun DifficultyPreview() {
    BuscaminasTheme(dynamicColor = false) {
        Difficulty(modifier = Modifier)
    }
}