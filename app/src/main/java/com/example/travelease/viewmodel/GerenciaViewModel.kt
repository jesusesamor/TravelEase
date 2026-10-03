package com.example.travelease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelease.model.ClienteDto
import com.example.travelease.repository.GerenciaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GerenciaViewModel : ViewModel() {

    // Conectamos con el obrero (Repository)
    private val repository = GerenciaRepository()

    // Variable de "Estado". Si esta variable cambia, Jetpack Compose redibuja la pantalla solo.
    private val _topClientes = MutableStateFlow<List<ClienteDto>>(emptyList())
    val topClientes: StateFlow<List<ClienteDto>> = _topClientes

    // AURELIO: Solo llama a esta función cuando la pantalla se abra
    fun cargarTopClientes() {
        viewModelScope.launch {
            // Vamos al servidor a traer los datos
            val resultado = repository.obtenerTopClientes()


            _topClientes.value = resultado
        }
    }
}