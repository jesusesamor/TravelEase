package com.example.travelease.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelease.R // Importación correcta de tu clase R

@Composable
fun ServiciosMenuScreen(
    onNavigateToVuelos: () -> Unit,
    onNavigateToTransporte: () -> Unit,
    onNavigateToHoteles: () -> Unit,
    onBackClick: () -> Unit
) {
    // Fondo gris claro de toda la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF1F5)) // Color de fondo gris sutil
            .padding(16.dp)
    ) {
        // 1. Cabecera Azul "Gestión de Servicios"
        Surface(
            color = Color(0xFF2196F3), // Azul institucional
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Gestión de Servicios",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Subtítulo
        Text(
            text = "Seleccione una categoría",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Fila 1: Vuelos y Transporte
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ServicioCard(
                title = "Vuelos",
                iconRes = R.drawable.servicio_vuelos,// Asegúrate de tener esta imagen en res/drawable
                onClick = onNavigateToVuelos,
                modifier = Modifier.weight(1f)
            )

            ServicioCard(
                title = "Transporte",
                iconRes = R.drawable.servicios_transporte, // Asegúrate de tener esta imagen en res/drawable
                onClick = onNavigateToTransporte,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Fila 2: Hoteles (Alineado a la izquierda)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ServicioCard(
                title = "Hoteles",
                iconRes = R.drawable.servicios_hotel, // Asegúrate de tener esta imagen en res/drawable
                onClick = onNavigateToHoteles,
                modifier = Modifier.weight(1f)
            )

            // Spacer para empujar "Hoteles" a la izquierda y mantener el tamaño
            Spacer(modifier = Modifier.weight(1f))
        }

        // Empuja el botón hacia la parte inferior
        Spacer(modifier = Modifier.weight(1f))

        // 5. Botón de Retroceso en la esquina inferior izquierda
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Regresar",
                modifier = Modifier.size(36.dp),
                tint = Color.DarkGray
            )
        }
    }
}

// Componente reutilizable para cada Tarjeta
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioCard(
    title: String,
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier.aspectRatio(0.9f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Icono de $title",
                modifier = Modifier.size(70.dp)
            )
        }
    }
}