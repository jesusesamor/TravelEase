package com.example.travelease.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

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
import com.example.travelease.viewmodel.ClienteViewModel

import com.example.travelease.view.MenuAsistenteScreen
import com.example.travelease.view.ClientesListScreen
import com.example.travelease.view.ClienteFormScreen

// 1. CLASE SEALED MEJORADA: Ahora acepta el ID en la ruta
sealed class RutasAsistente(val ruta: String) {
    object MenuAsistente : RutasAsistente("menu_asistente")
    object ListaClientes : RutasAsistente("lista_clientes")

    // 🚀 MAGIA: Ahora la ruta exige modo e id
    object FormularioCliente : RutasAsistente("formulario_cliente/{modo}/{id}") {
        fun crearRuta(modo: String, id: Int = 0) = "formulario_cliente/$modo/$id"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- 1. RUTA DE INGRESO ---
        composable("login") {
            LoginScreen(
                onNavigateToCliente = { navController.navigate("cliente") },
                onNavigateToGerencia = { navController.navigate("gerencia") },
                onNavigateToAsistente = { navController.navigate(RutasAsistente.MenuAsistente.ruta) }
            )
        }

        // --- 2. RUTAS DEL CLIENTE ---
        composable("cliente") {
            ClienteScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToMisReservas = { navController.navigate("mis_reservas") },
                onNavigateToSugerencias = { navController.navigate("sugerencias") },
                onNavigateToEncuesta = { navController.navigate("encuesta") }
            )
        }

        composable(route = "mis_reservas") {
            MisReservasScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEvaluar = { idReserva, paquete, factura, total ->
                    navController.navigate(route = "valoraciones/$idReserva/$paquete/$factura/$total")
                }
            )
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

            val viewModel: ClienteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

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

        // --- 4. NUEVAS RUTAS DEL ASISTENTE ---

        composable(RutasAsistente.MenuAsistente.ruta) {
            MenuAsistenteScreen(
                onNavigateToClientes = { navController.navigate(RutasAsistente.ListaClientes.ruta) },
                onCerrarSesion = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable(RutasAsistente.ListaClientes.ruta) {
            ClientesListScreen(
                onNavigateToNuevo = {
                    // 🚀 Al crear, pasamos ID = 0
                    navController.navigate(RutasAsistente.FormularioCliente.crearRuta("crear", 0))
                },
                onNavigateToEditar = { idCliente ->
                    // 🚀 Al editar, pasamos el ID real que nos manda la lista
                    navController.navigate(RutasAsistente.FormularioCliente.crearRuta("editar", idCliente))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 4.3 Formulario Dinámico (Crear / Editar)
        composable(
            route = RutasAsistente.FormularioCliente.ruta,
            arguments = listOf(
                navArgument("modo") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType } // 🚀 Le decimos a la ruta que espere un ID entero
            )
        ) { backStackEntry ->
            val modo = backStackEntry.arguments?.getString("modo") ?: "crear"
            val id = backStackEntry.arguments?.getInt("id") ?: 0 // Extraemos el ID

            ClienteFormScreen(
                modo = modo,
                clienteId = id, // 🚀 ¡Se lo inyectamos a tu pantalla por fin!
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}