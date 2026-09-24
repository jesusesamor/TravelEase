package com.example.travelease.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Importaciones de tus pantallas
import com.example.travelease.view.LoginScreen
import com.example.travelease.view.ClienteScreen
import com.example.travelease.view.GerenciaScreen
import com.example.travelease.view.MisReservasScreen
import com.example.travelease.view.ValoracionesScreen
import com.example.travelease.view.SugerenciasScreen
import com.example.travelease.view.EncuestaScreen
import com.example.travelease.view.PaquetesVendidosScreen
import com.example.travelease.view.IngresosDestinoScreen
import com.example.travelease.view.TopClientesScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelease.viewmodel.ClienteViewModel
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- 1. RUTA DE INGRESO ---
        composable("login") {
            LoginScreen(
                onNavigateToCliente = { navController.navigate("cliente") },
                onNavigateToGerencia = { navController.navigate("gerencia") }
            )
        }

        // --- 2. RUTAS DEL CLIENTE ---
        composable("cliente") {
            ClienteScreen(
                onNavigateBack = { navController.popBackStack() },
                // El botón "Valoraciones" en Figma lleva primero a la lista de reservas
                onNavigateToMisReservas = { navController.navigate("mis_reservas") },
                onNavigateToSugerencias = { navController.navigate("sugerencias") },
                onNavigateToEncuesta = { navController.navigate("encuesta") }
            )
        }

        composable(route = "mis_reservas") {
            MisReservasScreen(
                onNavigateBack = { navController.popBackStack() },
                // Aquí le decimos que envíe 4 datos (ID, Paquete, Factura, Total)
                onNavigateToEvaluar = { idReserva, paquete, factura, total ->
                    navController.navigate(route = "valoraciones/$idReserva/$paquete/$factura/$total")
                }
            ) // <--- ¡Este es el paréntesis que faltaba!
        }

        composable(
            route = "valoraciones/{idReserva}/{paquete}/{factura}/{total}",
            arguments = listOf(
                navArgument(name = "idReserva") { type = NavType.StringType },
                navArgument(name = "paquete") { type = NavType.StringType },
                navArgument(name = "factura") { type = NavType.StringType },
                navArgument(name = "total") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idReserva = backStackEntry.arguments?.getString("idReserva") ?: ""
            val paquete = backStackEntry.arguments?.getString("paquete") ?: ""
            val factura = backStackEntry.arguments?.getString("factura") ?: ""
            val total = backStackEntry.arguments?.getString("total") ?: ""
            // 1. Instanciamos el ViewModel directamente aquí
            val viewModel: ClienteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

            // 2. Llamamos a la pantalla usando las variables que ya extrajiste arriba
            ValoracionesScreen(
                viewModel = viewModel,
                idReserva = idReserva,
                paquete = paquete,
                factura = factura,
                total = total,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = "sugerencias") {
            SugerenciasScreen(
                viewModel = viewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // La ruta de la encuesta de satisfacción
        composable(route = "encuesta") {
            EncuestaScreen(
                viewModel = viewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- 3. RUTAS DE GERENCIA ---
        composable("gerencia") {
            GerenciaScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPaquetesVendidos = { navController.navigate("paquetes_vendidos") },
                onNavigateToIngresosDestino = { navController.navigate("ingresos_destino") },
                onNavigateToTopClientes = { navController.navigate("top_clientes") }
            )
        }

        composable("paquetes_vendidos") {
            PaquetesVendidosScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("ingresos_destino") {
            IngresosDestinoScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("top_clientes") {
            TopClientesScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}