package com.jimx.listitemselector.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.jimx.listitemselector.model.CategoryData

@Composable
fun ListAddLayout(
    item: CategoryData,
    isDisabled: Boolean,
    onSubmitClick: (CategoryData) -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier)
{
    var textFieldValue by remember { mutableStateOf(item.name) }

    Column(modifier = modifier)
    {
        Row(modifier = Modifier
            .fillMaxWidth())
        {
            IconButton(onClick = { onDismissClick() },
                enabled = !isDisabled,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.Close, "Close")
            }
            Text(
                text = "Add new category",
                fontSize = 24.sp,
                lineHeight = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        OutlinedTextField(
            textFieldValue,
            { textFieldValue = it },
            Modifier
                .padding(16.dp)
                .height(72.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text("Name") }
        )
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                onClick = { onSubmitClick(CategoryData(item.id, textFieldValue)) },
                enabled = !isDisabled,
                modifier = Modifier.padding(16.dp, 12.dp),
            ) {
                Text("Confirm")
            }
            OutlinedButton(
                onClick = { onDismissClick() },
                enabled = !isDisabled,
                modifier = Modifier.padding(16.dp, 12.dp),
            ) {
                Text("Dismiss")
            }
        }
    }
}