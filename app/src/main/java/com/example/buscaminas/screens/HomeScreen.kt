package com.example.buscaminas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.buscaminas.R
import com.example.buscaminas.ui.theme.BuscaminasTheme
import com.example.buscaminas.screens.Boton1

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Scaffold(
        modifier = modifier.fillMaxSize() // Asegúrate de que el Scaffold también llene el tamaño
    ) { innerPadding ->
        Column(
            // Aplica el innerPadding SOLO AQUÍ, al contenedor principal de tu contenido
            modifier = Modifier
                .padding(innerPadding) // Este padding es del Scaffold
                .fillMaxSize() // La columna llena el espacio restante
                .background(MaterialTheme.colorScheme.primaryContainer), // Fondo de la columna
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centra el contenido verticalmente si quieres
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp) // Tamaño de la imagen
                    .padding(bottom = 30.dp) // Padding solo para la imagen, no para el Scaffold
            )
            Text(
                text = "BUSCAMINAS",
                modifier = Modifier.padding(bottom = 50.dp), // Padding solo para el texto
                style = MaterialTheme.typography.titleLarge
            )
            Boton1(
                modifier = Modifier.padding(bottom = 30.dp),
                botonText = "Jugar",
                onClick = { navController?.navigate("DifficultyScreen") }
            )
            Boton1(
                modifier = Modifier.padding(bottom = 30.dp),
                botonText = "Estadísticas",
                onClick = { navController?.navigate("StatisticsScreen") }
            )
            Boton1(
                modifier = Modifier.padding(bottom = 30.dp),
                botonText = "Créditos",
                onClick = { navController?.navigate("CreditsScreen") }
            )
            Boton1(
                modifier = Modifier.padding(bottom = 10.dp),
                botonText = "salir" // Considera cómo manejarás el salir (ej. (context as Activity).finish())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    BuscaminasTheme(dynamicColor = false) {
        Home(modifier = Modifier)
    }
}