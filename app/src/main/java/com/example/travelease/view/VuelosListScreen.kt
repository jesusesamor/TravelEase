package com.example.travelease.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelease.model.Vuelo
import com.example.travelease.viewmodel.VueloViewModel

@Composable
fun VuelosListScreen(
    viewModel: VueloViewModel = viewModel(), // Inyectamos el ViewModel
    onNavigateBack: () -> Unit,
    onNavigateToAgregar: () -> Unit,
    onNavigateToEditar: (Int) -> Unit
) {
    val vuelos by viewModel.vuelos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // 🚀 Cargar datos reales al abrir la pantalla usando el token global
    LaunchedEffect(Unit) {
        viewModel.cargarVuelos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF1F5))
            .padding(16.dp)
    ) {
        Surface(
            color = Color(0xFF2196F3),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(90.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("Vuelos Registrados", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF2196F3))
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(vuelos) { vuelo ->
                    VueloCardItem(
                        vuelo = vuelo,
                        onEditar = { onNavigateToEditar(vuelo.id) },
                        onEliminar = { /* Lógica de eliminar próximamente */ }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onNavigateToAgregar,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Text("Agregar", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        IconButton(onClick = onNavigateBack, modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Regresar", modifier = Modifier.size(36.dp), tint = Color.DarkGray)
        }
    }
}

@Composable
fun VueloCardItem(
    vuelo: Vuelo,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    // Formateamos un poco la fecha si viene con formato raro de Django (T/Z)
    val fechaSalidaFormato = vuelo.fechaSalida.substringBefore("T")
    val fechaEntradaFormato = vuelo.fechaEntrada.substringBefore("T")

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "✈️ Aerolínea: ${vuelo.aerolinea}", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "📍 Ruta: ${vuelo.origen} ➔ ${vuelo.destino}", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "📅 Salida: $fechaSalidaFormato", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "📅 Entrada: $fechaEntradaFormato", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "💲 Precio: $ ${vuelo.precio}", fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onEditar) {
                    Text(text = "Editar", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                TextButton(onClick = onEliminar) {
                    Text(text = "Eliminar", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}