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
import androidx.compose.ui.platform.LocalContext // 👈 IMPORTACIÓN DEL CONTEXT
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

    // 👉 NUEVO: Obtenemos el contexto de Android para que el ViewModel guarde el token
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))

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
                // 🚀 AQUÍ ESTÁ LA MAGIA QUE SOLUCIONA EL 401
                // Le pasamos el context para que guarde el token ANTES de navegar
                viewModel.iniciarSesion(
                    context = context,
                    correo = usuario,
                    clave = contrasena,
                    onSuccess = {
                        // Navegamos SOLO cuando estamos 100% seguros de que el token se guardó
                        if (usuario.lowercase() == "admin") {
                            onNavigateToGerencia()
                        } else {
                            onNavigateToCliente()
                        }
                    }
                )
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

        Spacer(modifier = Modifier.height(40.dp))
    }
}