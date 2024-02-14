package com.hossain.wallet.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.presentation.components.SimpleStatement

@Composable
fun StatementSection(
    billCategory: BillCategory,
    statement: List<BillStatement>,
    modifier: Modifier = Modifier,
    onCategoryClick: (BillCategory) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { onCategoryClick(BillCategory.SPEND) },
                    modifier = Modifier
                        .weight(1f),
                    colors = if (billCategory == BillCategory.SPEND)
                        ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()

                ) {
                    Text("Spend")
                }
                OutlinedButton(
                    onClick = { onCategoryClick(BillCategory.DEBT) },
                    modifier = Modifier.weight(1f),
                    colors = if (billCategory == BillCategory.DEBT)
                        ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("Debt")
                }
                OutlinedButton(
                    onClick = { onCategoryClick(BillCategory.OWED) },
                    modifier = Modifier.weight(1f),
                    colors = if (billCategory == BillCategory.OWED)
                        ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("Owed")
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxHeight()
                    .padding(bottom = 24.dp),
            ) {
                if (statement.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Data Found")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(statement) { statement ->
                            SimpleStatement(statement)
                        }
                    }

                }
            }
        }
    }
}
