package com.example.buscaminas.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.buscaminas.R
import com.example.buscaminas.ui.theme.BuscaminasTheme

@Composable
fun Credits(
    modifier: Modifier = Modifier,
    navController: NavController? = null
){
    Scaffold(
        modifier = modifier.fillMaxSize() // Asegúrate que el Scaffold ocupe toda la pantalla
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Aplica el padding del Scaffold
                .fillMaxSize()         // El Column también debe llenar el espacio
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = "Créditos",
                modifier = Modifier.padding(bottom = 20.dp, top = 20.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "BUSCAMINAS",
                modifier = Modifier.padding(bottom = 50.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Desarrollado por",
                modifier = Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Juana Jaramillo Montoya - https://github.com/Darch02/",
                modifier = Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Y",
                modifier = Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Thomas Camilo Vanegas Acevedo - https://github.com/thomasvanegas",
                modifier = Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            // Spacer para empujar el botón hacia abajo
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
fun CreditsPreview() {
    BuscaminasTheme(dynamicColor = false) {
        Credits(modifier = Modifier)
    }
}