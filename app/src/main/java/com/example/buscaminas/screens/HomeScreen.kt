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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.R
import com.example.buscaminas.ui.theme.BuscaminasTheme
import com.example.buscaminas.screens.Boton1

@Composable
fun Home(
    modifier: Modifier = Modifier,
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
                text = "BUSCAMINAS",
                modifier.padding(innerPadding).padding(bottom = 50.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Boton1(
                modifier = modifier.padding(bottom = 20.dp),
                botonText = "Jugar"
            )
            Boton1(
                modifier = modifier.padding(bottom = 20.dp),
                botonText = "Estadísticas"
            )
            Boton1(
                modifier = modifier.padding(bottom = 20.dp),
                botonText = "Créditos"
            )
            Boton1(
                modifier = modifier.padding(bottom = 10.dp),
                botonText = "salir"
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