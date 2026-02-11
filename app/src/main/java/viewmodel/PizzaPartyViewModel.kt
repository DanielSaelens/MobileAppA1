package com.csci448.danielsaelens.danielsaelens_A1.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.csci448.danielsaelens.danielsaelens_A1.model.PizzaRepository
import kotlin.math.ceil

data class PizzaPartyUiState(
    val numPeople: String = "",
    val selectedHungerIndex: Int = 1,
    val selectedPizzaIndex: Int = 0,
    val totalPizzas: Int = 0,
    val totalCost: Double = 0.0
)

sealed class PizzaPartyIntent {
    data class UpdateNumPeople(val numPeople: String) : PizzaPartyIntent()
    data class UpdateHunger(val index: Int) : PizzaPartyIntent()
    data class UpdatePizza(val index: Int) : PizzaPartyIntent()
    object Calculate : PizzaPartyIntent()
}

class PizzaPartyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PizzaPartyUiState())
    val uiState: StateFlow<PizzaPartyUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: PizzaPartyIntent) {
        when (intent) {
            is PizzaPartyIntent.UpdateNumPeople -> {
                _uiState.value = _uiState.value.copy(numPeople = intent.numPeople)
            }
            is PizzaPartyIntent.UpdateHunger -> {
                _uiState.value = _uiState.value.copy(selectedHungerIndex = intent.index)
            }
            is PizzaPartyIntent.UpdatePizza -> {
                _uiState.value = _uiState.value.copy(selectedPizzaIndex = intent.index)
            }
            is PizzaPartyIntent.Calculate -> {
                val people = _uiState.value.numPeople.toIntOrNull() ?: 0
                val slicesPerPerson = when (_uiState.value.selectedHungerIndex) {
                    0 -> 1
                    1 -> 2
                    2 -> 4
                    else -> 2
                }
                val totalSlices = people * slicesPerPerson
                val totalPizzas = ceil(totalSlices / 8.0).toInt()
                val totalCost = totalPizzas * PizzaRepository.pizzaOptions[_uiState.value.selectedPizzaIndex].price
                _uiState.value = _uiState.value.copy(
                    totalPizzas = totalPizzas,
                    totalCost = totalCost
                )
            }
        }
    }
}