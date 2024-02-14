package com.hossain.wallet.presentation.components

import android.text.BoringLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillTypeChipBar(
    bill: BillType,
    showOnDialog: Boolean = false,
    onClick: (BillType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var billType = BillType.entries.toMutableList()
        if(showOnDialog) {
           billType.removeAt(0)
        }
        items(billType) { billType ->
            FilterChip(
                selected = bill.name == billType.name,
                onClick = {
                    onClick(billType)
                },
                label = {
                    Text(billType.name)
                }
            )
        }
    }
}