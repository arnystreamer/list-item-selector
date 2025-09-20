package com.jimx.listitemselector.ui.listitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jimx.listitemselector.model.ItemData

@Composable
fun ListItemAddLayout(
    item: ItemData,
    isDisabled: Boolean,
    onSubmitClick: (ItemData) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    var nameFieldValue by remember { mutableStateOf(item.name) }
    var descriptionFieldValue by remember { mutableStateOf(item.description ?: "") }

    Column(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth())
        {
            IconButton(
                onClick = { onDismissClick() },
                enabled = !isDisabled,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.Close, "Close")
            }
            Text(
                text = "Add new list item",
                fontSize = 24.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        OutlinedTextField(
            nameFieldValue,
            { nameFieldValue = it },
            Modifier
                .padding(16.dp)
                .height(72.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text("Name") }
        )
        OutlinedTextField(
            descriptionFieldValue,
            { descriptionFieldValue = it },
            Modifier
                .padding(16.dp)
                .height(196.dp)
                .fillMaxWidth(),
            singleLine = false,
            label = { Text("Description") }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                onClick = { onSubmitClick(ItemData(item.id, nameFieldValue, descriptionFieldValue, item.isExcluded)) },
                enabled = !isDisabled,
                modifier = Modifier.padding(16.dp, 12.dp)
            ) {
                Text("Confirm")
            }
            OutlinedButton(
                onClick = { onDismissClick() },
                enabled = !isDisabled,
                modifier = Modifier.padding(16.dp, 12.dp)
            ) {
                Text("Dismiss")
            }
        }
    }
}