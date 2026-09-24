package com.example.travelease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelease.R
import com.example.travelease.viewmodel.ClienteViewModel

@Composable
fun LoginScreen(
    viewModel: ClienteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToCliente: () -> Unit,
    onNavigateToGerencia: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val mensajeRespuesta = viewModel.loginMessage.value

    LaunchedEffect(key1 = mensajeRespuesta) {
        if (mensajeRespuesta.contains("Exitoso")) {
            if (usuario.lowercase() == "admin") {
                onNavigateToGerencia()
            } else {
                onNavigateToCliente()
            }

            // EL SECRETO: Borramos el éxito justo después de navegar
            // para que al regresar, el candado vuelva a estar cerrado.
            viewModel.cerrarSesion()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding() // <--- MAGIA 1: Detecta el teclado y empuja el diseño
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()), // <--- MAGIA 2: Permite hacer scroll con el dedo
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp)) // Espacio extra para que el scroll sea suave

        // --- TU LOGO REAL LEYENDO TU CARPETA DRAWABLE ---
        Image(
            painter = painterResource(id = R.drawable.logo_login_2),
            contentDescription = "Logo TravelEase",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bienvenido a\nTravelEase",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // 1. Hacemos la petición real al backend en Django
                viewModel.iniciarSesion(usuario, contrasena)

            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Iniciar sesión", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
        Text(text = mensajeRespuesta, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.height(40.dp)) // Espacio al final para que no quede pegado al borde inferior
    }
}