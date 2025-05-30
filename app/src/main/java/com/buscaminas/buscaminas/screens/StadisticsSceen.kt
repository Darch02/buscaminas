package com.buscaminas.buscaminas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.buscaminas.buscaminas.R
import com.buscaminas.buscaminas.models.StatisticsData
import com.buscaminas.buscaminas.ui.theme.BuscaminasTheme

@Composable
fun Statistics(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    statisticsData: StatisticsData
){
    val partidas by statisticsData.partidas.collectAsState(initial = 0)
    val victorias by statisticsData.victorias.collectAsState(initial = 0)
    val record by statisticsData.record.collectAsState(initial = 0)

    val partidas_medio by statisticsData.partidas_medio.collectAsState(initial = 0)
    val victorias_medio by statisticsData.victorias_medio.collectAsState(initial = 0)
    val record_medio by statisticsData.record_medio.collectAsState(initial = 0)

    val partidas_dificil by statisticsData.partidas_dificil.collectAsState(initial = 0)
    val victorias_dificil by statisticsData.victorias_dificil.collectAsState(initial = 0)
    val record_dificil by statisticsData.record_dificil.collectAsState(initial = 0)
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
                modifier.padding(bottom = 10.dp).size(150.dp)
            )
            Text(
                text = "Estadísticas",
                modifier.padding(innerPadding).padding(bottom = 10.dp),
                style = MaterialTheme.typography.titleLarge
            )
            DataContainer(
                levelName = "Fácil",
                matches = partidas.toString(),
                wins = victorias.toString(),
                record = record.toString(),
                modifier = Modifier.width(300.dp)

            )
            DataContainer(
                levelName = "Medio",
                matches = partidas_medio.toString(),
                wins = victorias_medio.toString(),
                record = record_medio.toString(),
                modifier = Modifier.width(300.dp)

            )
            DataContainer(
                levelName = "Difícil",
                matches = partidas_dificil.toString(),
                wins = victorias_dificil.toString(),
                record = record_dificil.toString(),
                modifier = Modifier.width(300.dp)

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
fun StatisticsPreviews() {
    BuscaminasTheme(dynamicColor = false) {
        //Statistics(modifier = Modifier)
    }
}