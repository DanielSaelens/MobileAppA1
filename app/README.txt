
Daniel Saelens

1. 
By using Kotlin string templates with $$. The first $ is a literal 
dollar sign and the second $ starts Kotlin variable interpolation.
Example: "$${String.format("%.2f", uiState.totalCost)}"

2. 
For RadioButtons, I utilzied a forEach loop creating a Row with a 
RadioButton and Text for each hunger option. Then I tracked the selected 
option using selectedHungerIndex in the ViewModel.
For the Dropdown, I used a DropdownMenu inside a Box with a 
clickable overlay. I tracked the selected pizza using 
selectedPizzaIndex in the ViewModel.

3. Stateful: PizzaPartyViewModel owns all the state.
Stateless: PizzaPartyScreen, all Text, Button, RadioButton, 
and DropdownMenuItem composables they just receive data.

4. numPeople, selectedHungerIndex, selectedPizzaIndex, 
totalPizzas, totalCost

5. Why was each Intent subtype necessary?
UpdateNumPeople -> updates people count when user types
UpdateHunger -> updates hunger level when radio button selected
UpdatePizza -> updates pizza type when dropdown item selected
Calculate ->triggers pizza and cost calculation
Each Intent corresponds to a user action that needs to 
update the UI state.