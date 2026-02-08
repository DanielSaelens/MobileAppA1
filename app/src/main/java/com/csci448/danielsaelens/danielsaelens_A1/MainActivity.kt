package com.csci448.danielsaelens.danielsaelens_A1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown



data class Pizza(
    val name: String,
    val price: Double

)
val pizzaOptions = listOf(
    Pizza(name = "Cheese", price = 8.50),
    Pizza(name = "Detroit", price = 14.50),
    Pizza(name = "Hawaiian", price = 11.50),
    Pizza(name = "Margherita", price = 9.75),
    Pizza(name = "Pepperoni", price = 10.25),
    Pizza(name = "Sausage", price = 10.25),
    Pizza(name = "Sicilian", price = 15.00),


)

@Composable
fun PizzaPartyScreen() {
    var numPeople by remember { mutableStateOf("") }
    var selectedHunger by remember { mutableStateOf("Medium") }
    var selectedPizzaIndex by remember { mutableStateOf(0) }
    var totalPizzas by remember { mutableStateOf(0) }
    var totalCost by remember { mutableStateOf(0.0) }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Number of People?")
            Spacer(modifier = Modifier.width(20.dp))
            TextField(
                value = numPeople,
                onValueChange = { numPeople = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(200.dp)

            )
        }
        Text("How hungry is everyone?")
        val hungerOptions = listOf("Light", "Medium", "Ravenous")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                hungerOptions.forEach { option ->
                    Row {
                        RadioButton(
                            selected = (option == selectedHunger),
                            onClick = { selectedHunger = option },
                        )
                        Text(option)
                    }

                }
            }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Pizza")
            Spacer(modifier = Modifier.width(16.dp))

            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.clickable { expanded = true }) {
                TextField(
                    value = "${pizzaOptions[selectedPizzaIndex].name} ($${String.format("%.2f", pizzaOptions[selectedPizzaIndex].price)} ea)",
                    onValueChange = { },
                    readOnly = true,
                    //enabled = false,
                    modifier = Modifier.width(280.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown arrow"
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
                    pizzaOptions.forEachIndexed { index, pizza ->
                        DropdownMenuItem(
                            text = { Text("${pizza.name} - $${String.format("%.2f", pizza.price)}") },
                            //text = { Text("${pizza.name} - $${pizza.price}") },
                            onClick = {
                                selectedPizzaIndex = index
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
            Button(onClick = {
                val people = numPeople.toIntOrNull() ?: 0

                val slicesPerPerson = when(selectedHunger) {
                    "Light" -> 1
                    "Medium" -> 2
                    "Ravenous" -> 4
                    else -> 2
                }

                val totalSlices = people * slicesPerPerson
                totalPizzas = kotlin.math.ceil(totalSlices / 8.0).toInt()
                totalCost = totalPizzas * pizzaOptions[selectedPizzaIndex].price
            }) {
                Text("Calculate!")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Pizzas Needed:")
            Text("$totalPizzas")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Cost:")
            Text("$${String.format("%.2f", totalCost)}")
        }
    }
}






class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PiazzaPartyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PizzaPartyScreen()
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