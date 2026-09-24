package com.example.travelease.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelease.viewmodel.ClienteViewModel

@Composable
fun SugerenciasScreen(viewModel: ClienteViewModel, onNavigateBack: () -> Unit) {
    var sugerencia by remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Sugerencias", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "¿Tienes una sugerencia?\n¡Escríbela! Tu opinión es importante para seguir mejorando nuestro servicio",
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = sugerencia,
            onValueChange = { sugerencia = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // 1. Enviamos el dato a Django
                viewModel.enviarSugerencia(textoMensaje = sugerencia)

                // 2. Mostramos el mensaje flotante (SIN nombrar los parámetros)
                // 2. Mostramos el mensaje flotante (SIN nombrar los parámetros)
                android.widget.Toast.makeText(context, "¡Sugerencia enviada, gracias!", android.widget.Toast.LENGTH_SHORT).show()

                // 3. Lo regresamos a la pantalla anterior
                onNavigateBack()
            }, // <--- ¡Ojo aquí! Usamos coma, no punto.
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Text("Enviar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        // 1. Este Spacer funciona como un resorte que empuja la flecha hacia el fondo
        Spacer(modifier = Modifier.weight(1f))

        // 2. La barra inferior con tu botón de retroceso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.Gray
                )
            }
        }
    } // <--- Esta llave cierra tu Column principal
} // <--- Esta llave final cierra tu función SugerenciasScreen





