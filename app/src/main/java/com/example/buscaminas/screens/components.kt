package com.example.buscaminas.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Boton1(
    modifier : Modifier = Modifier,
    botonText : String,
    onClick : () -> Unit = {}
){
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(3.dp,MaterialTheme.colorScheme.onSurface)

    ) {
        Text(
            text = botonText,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun Boton2(
    levelName: String,
    levelDescription: String
) {
    Surface(
        modifier = Modifier
            .padding(8.dp), // Espacio alrededor del Surface
        shape = RoundedCornerShape(12.dp), // Aplica las esquinas redondeadas directamente al Surface
        color = MaterialTheme.colorScheme.inverseSurface,
        tonalElevation = 4.dp, // Opcional: añade elevación para un efecto de sombra
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp), // Padding interno para el texto
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = levelName,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 20.sp
                )
                Text(
                    text = levelDescription,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun DataContainer(
    levelName: String,
    matches: String,
    wins: String,
    record : String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(8.dp), // Espacio alrededor del Surface
        shape = RoundedCornerShape(12.dp), // Aplica las esquinas redondeadas directamente al Surface
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface), // Opcional: añade elevación para un efecto de sombra
    ) {
        Box(
            modifier = modifier
                .padding(horizontal = 20.dp, vertical = 10.dp), // Padding interno para el texto
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Nivel $levelName",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 24.sp
                )
                Text(
                    text = "Partidas: $matches",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )
                Text(
                    text = "Victorias: $wins",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )
                Text(
                    text = "Record: $record",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )
            }
        }
    }
}

