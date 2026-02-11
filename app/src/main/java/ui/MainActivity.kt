package com.csci448.danielsaelens.danielsaelens_A1.ui
import androidx.compose.runtime.collectAsState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csci448.danielsaelens.danielsaelens_A1.ui.theme.PiazzaPartyTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.res.stringResource
import com.csci448.danielsaelens.danielsaelens_A1.R
import com.csci448.danielsaelens.danielsaelens_A1.model.PizzaRepository
import com.csci448.danielsaelens.danielsaelens_A1.viewmodel.PizzaPartyIntent
import com.csci448.danielsaelens.danielsaelens_A1.viewmodel.PizzaPartyUiState
import com.csci448.danielsaelens.danielsaelens_A1.viewmodel.PizzaPartyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Tracking Pizza types and prices

@Composable
fun PizzaPartyScreen(uiState: PizzaPartyUiState, onIntent: (PizzaPartyIntent) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.num_people_label))
            Spacer(modifier = Modifier.width(20.dp))
            TextField(
                value = uiState.numPeople,
                onValueChange = { onIntent(PizzaPartyIntent.UpdateNumPeople(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(200.dp)
            )
        }

        Text(stringResource(R.string.how_hungry_label))
        val hungerOptions = listOf(
            stringResource(R.string.hunger_light),
            stringResource(R.string.hunger_medium),
            stringResource(R.string.hunger_ravenous)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            hungerOptions.forEachIndexed { index, option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (index == uiState.selectedHungerIndex),
                        onClick = { onIntent(PizzaPartyIntent.UpdateHunger(index)) },
                    )
                    Text(option)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.pizza_label))
            Spacer(modifier = Modifier.width(16.dp))
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.clickable { expanded = true }) {
                TextField(
                    value = "${PizzaRepository.pizzaOptions[uiState.selectedPizzaIndex].name} ($${String.format("%.2f", PizzaRepository.pizzaOptions[uiState.selectedPizzaIndex].price)} ea)",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.width(280.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.dropdown_arrow_description)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { expanded = true }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    PizzaRepository.pizzaOptions.forEachIndexed { index, pizza ->
                        DropdownMenuItem(
                            text = { Text("${pizza.name} - $${String.format("%.2f", pizza.price)}") },
                            onClick = {
                                onIntent(PizzaPartyIntent.UpdatePizza(index))
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onIntent(PizzaPartyIntent.Calculate) }) {
                Text(stringResource(R.string.calculate_button))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.total_pizzas_label))
            Text("${uiState.totalPizzas}")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.total_cost_label))
            Text("$${String.format("%.2f", uiState.totalCost)}")
        }
    }
}






class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PiazzaPartyTheme {
                val viewModel: PizzaPartyViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    PizzaPartyScreen(
                        uiState = uiState,
                        onIntent = { viewModel.handleIntent(it) }
                    )
                }
            }
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PiazzaPartyTheme {
        Greeting("Android")
    }
}