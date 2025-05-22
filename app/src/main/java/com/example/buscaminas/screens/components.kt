package com.example.buscaminas.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Boton1(
    modifier : Modifier = Modifier,
    botonText : String
){
    OutlinedButton(
        onClick = { /*TODO*/ },
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