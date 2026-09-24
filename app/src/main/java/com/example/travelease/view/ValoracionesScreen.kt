package com.example.travelease.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelease.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValoracionesScreen(
    // ¡AQUÍ ESTÁ LA MAGIA! Ya estamos exigiendo los 4 parámetros exactos
    viewModel: ClienteViewModel,
    idReserva: String,
    paquete: String,
    factura: String,
    total: String,
    onNavigateBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var calificacion by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }

    val colorFondo = Color(0xFFEDF2F7)
    val colorAzul = Color(0xFF63B3ED)

    Box(
        modifier = Modifier.fillMaxSize().background(colorFondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                // 1. ESTO HACE QUE LA PANTALLA SE PUEDA DESLIZAR ARRIBA Y ABAJO:
                .verticalScroll(rememberScrollState())
                // 2. ESTO EMPUJA EL CONTENIDO HACIA ARRIBA CUANDO SALE EL TECLADO:
                .imePadding()
        ) {
            Text(
                text = "Valoraciones", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // --- TU TARJETA ORIGINAL REUTILIZADA ---
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(text = paquete, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2196F3))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "ID Reserva: $idReserva", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Factura N°: $factura", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Total: C$ $total", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- ESTRELLAS ---
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Puntúa tu viaje", fontWeight = FontWeight.Bold, color = Color.Black)
                    Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.Center) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= calificacion) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Estrella $i",
                                // ¡Aquí está la magia del color!
                                tint = if (i <= calificacion) Color(0xFFFFC107) else Color.LightGray,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clickable { calificacion = i }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- COMENTARIOS ---
            Text(text = "Déjanos tu comentario", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = comentario, onValueChange = { comentario = it },
                modifier = Modifier.fillMaxWidth().height(120.dp), shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White, focusedContainerColor = Color.White,
                    focusedBorderColor = colorAzul, unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // 1. ¡AHORA SÍ ENVIAMOS LOS DATOS A DJANGO!
                    // Usamos las variables que ya tienes en tu pantalla
                    viewModel.enviarvaloracion(
                        idDeLaReserva = idReserva.toIntOrNull() ?: 1,
                        paquete = paquete,
                        estrellas = calificacion,
                        comentario = comentario
                    )

                    // 2. Mostrar el mensaje flotante confirmando la acción al usuario
                    android.widget.Toast.makeText(context, "¡Valoración enviada exitosamente!", android.widget.Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(size = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorAzul)
            ) {
                Text(text = "Enviar", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Regresar"
                )
            }
    }
}
}