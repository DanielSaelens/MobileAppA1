package com.csci448.danielsaelens.danielsaelens_A1.model
data class Pizza(
    val name: String,
    val price: Double
)

object PizzaRepository {
    val pizzaOptions = listOf(
        Pizza(name = "Cheese", price = 8.50),
        Pizza(name = "Detroit", price = 14.50),
        Pizza(name = "Hawaiian", price = 11.50),
        Pizza(name = "Margherita", price = 9.75),
        Pizza(name = "Pepperoni", price = 10.25),
        Pizza(name = "Sausage", price = 10.25),
        Pizza(name = "Sicilian", price = 15.00)
    )
}