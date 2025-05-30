package com.example.buscaminas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.buscaminas.R
import com.example.buscaminas.ui.theme.BuscaminasTheme
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val context = LocalContext.current
        val activity = context as? Activity
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo Buscaminas",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )
            
            Text(
                text = "BUSCAMINAS",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            Spacer(modifier = Modifier.weight(0.1f))
            
            // Botones del menú
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Boton1(
                    modifier = Modifier.fillMaxWidth(),
                    botonText = "Jugar",
                    onClick = { navController?.navigate("DifficultyScreen") }
                )
                
                Boton1(
                    modifier = Modifier.fillMaxWidth(),
                    botonText = "Estadísticas",
                    onClick = { navController?.navigate("StatisticsScreen") }
                )
                
                Boton1(
                    modifier = Modifier.fillMaxWidth(),
                    botonText = "Créditos",
                    onClick = { navController?.navigate("CreditsScreen") }
                )
                
                Boton1(
                    modifier = Modifier.fillMaxWidth(),
                    botonText = "Salir",
                    onClick = { activity?.finish() }
                )
            }
            
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    BuscaminasTheme(dynamicColor = false) {
        Home()
    }
}