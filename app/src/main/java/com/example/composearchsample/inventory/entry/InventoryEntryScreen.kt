package com.example.composearchsample.inventory.entry


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.composearchsample.R
import com.example.composearchsample.components.CustomDialog
import com.example.composearchsample.components.CustomToolbar
import com.example.composearchsample.data.uimodels.InventoryDetails
import com.example.composearchsample.ui.theme.ComposeArchSampleTheme
import java.util.Currency
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryEntryScreen(
    onEvent: (InventoryEntryEvent) -> Unit,
    state: State<InventoryEntryUiState>,
    onNavBack: () -> Unit,
    onInventorySavedSuccessfully: () -> Unit,
    canNavigateBack: Boolean = true,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(R.string.item_entry_title),
                isNavIcon = canNavigateBack,
                onBack = onNavBack
            )
        }
    ) { innerPadding ->
        InventoryDetailsBody(
            itemUiState = state.value,
            onItemValueChange = { inventoryDetails ->
                onEvent.invoke(
                    InventoryEntryEvent.UpdateUiState(
                        inventoryDetails
                    )
                )
            },
            onSaveClick = {
                onEvent.invoke(InventoryEntryEvent.AddInventory)
            },
            onInventorySavedSuccessfully,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InventoryDetailsBody(
    itemUiState: InventoryEntryUiState,
    onItemValueChange: (InventoryDetails) -> Unit,
    onSaveClick: () -> Unit,
    onInventorySavedSuccessfully: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        InventoryDetailsForm(
            itemDetails = itemUiState.inventoryDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = itemUiState.isValidInventory,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
        when {
            itemUiState.isLoading -> CircularProgressIndicator(color = Color.Blue)
            itemUiState.isInventoryDetailsAddedSuccessfully -> onInventorySavedSuccessfully()
            itemUiState.isError -> CustomDialog(
                title = "Error",
                message = "Something went wrong"
            )
        }
    }
}

@Composable
fun InventoryDetailsForm(
    itemDetails: InventoryDetails,
    modifier: Modifier = Modifier,
    onValueChange: (InventoryDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange = { onValueChange(itemDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.item_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.price,
            onValueChange = { onValueChange(itemDetails.copy(price = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.item_price_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.quantity,
            onValueChange = { onValueChange(itemDetails.copy(quantity = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.quantity_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    ComposeArchSampleTheme {
        InventoryDetailsBody(itemUiState = InventoryEntryUiState(
            InventoryDetails(
                name = "Item name", price = "10.00", quantity = "5"
            )
        ), onItemValueChange = {}, onSaveClick = {}, onInventorySavedSuccessfully = { }
        )
    }
}
