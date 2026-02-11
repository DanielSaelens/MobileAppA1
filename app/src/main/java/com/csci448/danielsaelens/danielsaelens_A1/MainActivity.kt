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



// Tracking Pizza types and prices
data class Pizza(
    val name: String,
    val price: Double

)
// List of Objects for Pizza types and Pizza prices
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
    // These represent our state variables for tracking user input and calculated results
    var numPeople by remember { mutableStateOf("") }
    var selectedHunger by remember { mutableStateOf("Medium") }
    var selectedPizzaIndex by remember { mutableStateOf(0) }
    var totalPizzas by remember { mutableStateOf(0) }
    var totalCost by remember { mutableStateOf(0.0) }


    // Vertical stack that holds all UI components for the screen
    Column(modifier = Modifier.padding(16.dp)) {

        // This sets up the container for the Number of People? text and the input
        Row(
            // Stretches the row to full width and vertically centers its contents
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text label, spacer, and input field for the number of people
            Text("Number of People?")
            Spacer(modifier = Modifier.width(20.dp))
            // The text label and spacing before the input field
            // Input field for number of people, connected to state, with number-only keyboard
            TextField(
                // Used for our numPeople state
                value = numPeople,
                // Updates the numPeople state whenever the user types
                onValueChange = { numPeople = it },
                // Shows a number-only keyboard for input
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                // Sets the width of the input field
                modifier = Modifier.width(200.dp)

            )
        }







        // Builds the hunger level selection with radio buttons
        Text("How hungry is everyone?")
        val hungerOptions = listOf("Light", "Medium", "Ravenous")
        // Where the spacing happens for the hunger levels
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Loops through each hunger option
                hungerOptions.forEach { option ->
                    Row {
                        RadioButton(
                            // Radio button that checks if this option is selected
                            selected = (option == selectedHunger),
                            onClick = { selectedHunger = option },
                        )
                        // Displays the label next to the radio button
                        Text(option)
                    }

                }
            }

        // Container for the pizza type dropdown, full width and vertically centered
        // Row { } = horizontal container, Column { } = vertical container,
        // Box { } = layered container. Children go inside the curly braces.
        // Row(){} parameterized container, same with the rest
        Row(
            // For lining and spacing
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pizza label and spacing
            Text("Pizza")
            Spacer(modifier = Modifier.width(16.dp))
            // Tracks whether the dropdown is open or closed
            var expanded by remember { mutableStateOf(false) }
            // Clickable container that opens the dropdown when tapped
            Box(modifier = Modifier.clickable { expanded = true }) {
                // Read-only field that displays the selected pizza type, price, and a dropdown arrow
                TextField(
                    // Displays the currently selected pizza name and price in the field
                    // String interpolation: grabs the selected pizza's name and formats its price to 2 decimal places
                    value = "${pizzaOptions[selectedPizzaIndex].name} ($${String.format("%.2f", pizzaOptions[selectedPizzaIndex].price)} ea)",
                    // Just used as placeholder
                    onValueChange = { },
                    // Use can see text but not edit it
                    readOnly = true,
                    // Sets the width of the text field
                    modifier = Modifier.width(280.dp),

                    // Adds a dropdown arrow icon on the right side of the field
                    // trailingIcon = right side of field, leadingIcon = left side.
                    // This adds a dropdown arrow on the right
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown arrow"
                        )
                    }
                )
                // Invisible overlay that captures taps to open the dropdown
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { expanded = true }
                )
                // Opens the menu and closes it
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // Loops through the pizzas, displays each one, and handles selection on click
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

        // Row container "horizontal"
        Row(
            // Centering for the calculate button
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                // Safely converts input to an integer, defaults to 0 if invalid (Elvis operator)
                val people = numPeople.toIntOrNull() ?: 0
                // Like a switch statement
                val slicesPerPerson = when(selectedHunger) {
                    "Light" -> 1
                    "Medium" -> 2
                    "Ravenous" -> 4
                    else -> 2
                }
                // Calculation for hunger cost
                val totalSlices = people * slicesPerPerson
                totalPizzas = kotlin.math.ceil(totalSlices / 8.0).toInt()
                totalCost = totalPizzas * pizzaOptions[selectedPizzaIndex].price
            }) {
                // Calculate text button
                Text("Calculate!")
            }
        }
        // Row container "horizontal"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Pizzas Needed:")
            Text("$totalPizzas")
        }
        // Row container "horizontal"
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