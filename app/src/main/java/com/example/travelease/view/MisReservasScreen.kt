package com.example.travelease.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelease.model.Reserva
import com.example.travelease.viewmodel.ClienteViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.ArrowBack
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ReservaItemCard(reserva: Reserva, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 1. Nombre del Paquete (Usando tu azul original)
            Text(
                text = reserva.nombre_paquete,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 2. ID de Reserva (Usando tu negro y SemiBold original)
            Text(
                text = "ID Reserva: ${reserva.idReserva}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 3. Número de Factura (Usando tu DarkGray original)
            Text(
                text = "Factura N°: ${reserva.numero_factura}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 4. Total (En verde para resaltar que es dinero)
            Text(
                text = "Total: C$ ${reserva.total}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Composable
fun MisReservasScreen(
    viewModel: ClienteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToEvaluar: (String, String, String, String) -> Unit
) {
    // 1. La orden va AQUÍ, adentro de las llaves { } de la función
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.cargarReservas()
    }

    // 2. Leemos los datos correctos del ViewModel (usando los nombres nuevos)
    val listaReservas by viewModel.listaReservas.collectAsState()
    val estadoMensaje by viewModel.estadoMensaje.collectAsState()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FA)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(Color(0xFF2196F3))
                        .padding(vertical = 28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Mis Reservas",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (listaReservas.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (estadoMensaje.isNotEmpty()) estadoMensaje else "La lista llegó vacía",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    // 1. Extraemos la lista limpia ANTES de entrar al diseño visual
                    // (Nota: Si la palabra "value" se te pone roja aquí, simplemente
                    // bórrala y déjalo como "val misDatos = listaReservas")
                    val misDatos: List<Reserva> = listaReservas

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        // 2. Le pasamos el tamaño exacto usando nuestra variable limpia
                        items(count = misDatos.size) { index ->

                            // 3. Extraemos cada reserva sin que el compilador se enrede
                            val reserva = misDatos[index]

                            ReservaItemCard(
                                reserva = reserva,
                                onClick = {
                                    onNavigateToEvaluar(
                                        reserva.idReserva.toString(), // Porque es Int
                                        reserva.nombre_paquete,       // Porque ya es String
                                        reserva.numero_factura,       // Porque ya es String
                                        reserva.total.toString()      // Porque es Double
                                    )   // <--- Este es el truco infalible


                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Este componente invisible empuja el botón hasta el fondo de la pantalla

                IconButton(
                    onClick = { onNavigateBack() },
                    modifier = Modifier
                        .align(Alignment.Start) // Lo pega a la izquierda de forma correcta
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}


