package com.exemplo.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraIMCApp()
        }
    }
}

@Composable
fun CalculadoraIMCApp() {
    var pesoInput by remember { mutableStateOf("") }
    var alturaInput by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<Double?>(null) }
    var categoria by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = pesoInput,
            onValueChange = { pesoInput = it },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = alturaInput,
            onValueChange = { alturaInput = it },
            label = { Text("Altura (m)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val peso = pesoInput.toDoubleOrNull()
                val altura = alturaInput.toDoubleOrNull()
                if (peso != null && altura != null && altura > 0) {
                    resultado = calcularIMC(peso, altura)
                    categoria = categorizarIMC(resultado!!)
                } else {
                    resultado = null
                    categoria = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular IMC")
        }
        Spacer(modifier = Modifier.height(16.dp))
        resultado?.let {
            Text("Seu IMC é: %.2f".format(it), fontSize = 20.sp)
            categoria?.let { cat ->
                Text("Categoria: $cat", fontSize = 18.sp)
            }
        }
    }
}

fun calcularIMC(peso: Double, altura: Double): Double {
    return peso / (altura * altura)
}

fun categorizarIMC(imc: Double): String {
    return when {
        imc < 18.5 -> "Abaixo do peso"
        imc in 18.5..24.9 -> "Peso normal"
        imc in 25.0..29.9 -> "Sobrepeso"
        else -> "Obesidade"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculadoraIMCApp() {
    CalculadoraIMCApp()
}
