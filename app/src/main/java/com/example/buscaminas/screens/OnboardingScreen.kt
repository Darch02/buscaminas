package com.example.buscaminas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.Greeting
import com.example.buscaminas.R
import com.example.buscaminas.ui.theme.BuscaminasTheme

@Composable
fun OnBoarding(
    modifier: Modifier = Modifier,
){
    Scaffold(
        modifier = modifier
    ){
        innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(colorScheme.primaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier.padding(innerPadding).size(150.dp)
            )
            Text(
                text = "Buscaminas",
                modifier.padding(innerPadding),
                style = MaterialTheme.typography.titleLarge
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun OnboaringPreview() {
    BuscaminasTheme(dynamicColor = false) {
       OnBoarding(modifier = Modifier)
    }
}


