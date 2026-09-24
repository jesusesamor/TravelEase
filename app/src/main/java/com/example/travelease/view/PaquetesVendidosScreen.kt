package com.example.travelease.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaquetesVendidosScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Paquetes más vendidos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Cada boleto representa un paquete y sus reservas", fontSize = 14.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(32.dp))

        // Lista de paquetes
        PaqueteItem(numero = "1", nombre = "Vacaciones\nAztecas", reservas = "313")
        Spacer(modifier = Modifier.height(16.dp))
        PaqueteItem(numero = "2", nombre = "Miami\nVibrante", reservas = "313")
        Spacer(modifier = Modifier.height(16.dp))
        PaqueteItem(numero = "3", nombre = "Aventura en\nla Amazonía", reservas = "218")

        Spacer(modifier = Modifier.weight(1f))

        // BARRA INFERIOR (Solo flecha de atrás)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Atrás", tint = Color.Gray, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun PaqueteItem(numero: String, nombre: String, reservas: String) {
    Row(
        modifier = Modifier.fillMaxWidth().border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)).background(Color.White).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(32.dp).background(Color(0xFFE3F2FD), CircleShape), contentAlignment = Alignment.Center) {
            Text(text = numero, color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))

        Box(modifier = Modifier.background(Color(0xFF1565C0), RoundedCornerShape(8.dp)).padding(horizontal = 16.dp, vertical = 12.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = reservas, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "RESERVAS", color = Color.White, fontSize = 8.sp)
            }
        }
    }
}