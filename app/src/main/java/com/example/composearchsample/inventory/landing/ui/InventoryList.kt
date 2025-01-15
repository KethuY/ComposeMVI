package com.example.composearchsample.inventory.landing.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.composearchsample.R
import com.example.composearchsample.data.local.inventory.Inventory

@Composable
 fun InventoryList(
    itemList: List<Inventory>,
    onItemClick: (Inventory) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        userScrollEnabled = true
    ) {
        items(items = itemList, key = { it.id }) { item ->
            InventoryItem(
                item = item,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                onItemClick
            )
        }
    }
}