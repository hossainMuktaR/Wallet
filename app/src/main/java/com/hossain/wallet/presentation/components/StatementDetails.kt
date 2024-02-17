package com.hossain.wallet.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.utils.toStringDate

@Composable
fun StatementDetails(
    billStatement: BillStatement,
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDeleteClick: (BillStatement) -> Unit,
    onEditClick: (BillStatement) -> Unit
) {

    Box(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    FilterChip(
                        selected = true,
                        onClick = { },
                        label = {
                            Text("${billStatement.type}")
                        },
                        colors = FilterChipDefaults.filterChipColors().copy(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    )
                }
                IconButton(onClick = {onEditClick(billStatement)}) {
                    Box(
                        modifier = Modifier
                            .size(width = 48.dp, height = 48.dp)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Statement"
                        )
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Amount: ")
                Text("${billStatement.amount} ৳")
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Message: ")
                Text("${billStatement.title}")
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Note: ")
                Text("${billStatement.note}")
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Date: ")
                Text(billStatement.date.toStringDate())
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = onCancelClick) {
                    Text(text = "Cancel")
                }
                Button(onClick = { onDeleteClick(billStatement) }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}