package com.example.composearchsample.inventory.landing


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composearchsample.R
import com.example.composearchsample.components.CustomToolbar
import com.example.composearchsample.data.local.inventory.Inventory
import com.example.composearchsample.inventory.empty.NoInventoriesScreen
import com.example.composearchsample.inventory.landing.ui.InventoryList


/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryLandingScreen(
    onEvent: (InventoryLandingEvent) -> Unit,
    state: State<InventoryLandingUiState>,
    navigateToInventoryEntry: () -> Unit,
    navigateToInventoryUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onAppExist: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    if (!state.value.isApiCalled) {
        onEvent.invoke(InventoryLandingEvent.GetInventories)
    }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomToolbar(title = stringResource(R.string.inventories))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInventoryEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->

        if (state.value.inventories.isEmpty()) {
            NoInventoriesScreen(PaddingValues(16.dp),onAppExist)
        } else {
            InventoryLandingBody(
                itemList = state.value.inventories,
                onItemClick = navigateToInventoryUpdate,
                modifier = modifier.fillMaxSize(),
                contentPadding = innerPadding,
                onAppExist
            )
        }
    }
}

@Composable
private fun InventoryLandingBody(
    itemList: List<Inventory>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onAppExist: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        InventoryList(
            itemList = itemList,
            onItemClick = { onItemClick(it.id) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
        BackHandler {
            onAppExist()
        }
    }
}