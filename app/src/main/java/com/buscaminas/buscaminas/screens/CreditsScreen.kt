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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.buscaminas.buscaminas.R
import com.buscaminas.buscaminas.ui.theme.BuscaminasTheme

@Composable
fun Credits(
    modifier: Modifier = Modifier,
    navController: NavController? = null
){
    Scaffold(
        modifier = modifier.fillMaxSize()
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 24.dp), // Padding horizontal general
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = "Créditos",
                modifier = Modifier.padding(vertical = 32.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "BUSCAMINAS",
                modifier = Modifier.padding(vertical = 32.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Desarrollado por",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Juana Jaramillo Montoya",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "https://github.com/Darch02/",
                modifier = Modifier.padding(bottom = 24.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Y",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Thomas Vanegas Acevedo",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "https://github.com/thomasvanegas",
                modifier = Modifier.padding(bottom = 32.dp),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.weight(1f))

            Boton1(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
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