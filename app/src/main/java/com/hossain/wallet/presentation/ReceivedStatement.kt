package com.hossain.wallet.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.presentation.components.SimpleStatement

@Composable
fun ReceivedStatement(
    statement: List<BillStatement>,
    modifier: Modifier = Modifier,
    onStatementClick: (BillStatement)-> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
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
                        SimpleStatement(
                            statement,
                            onClick = onStatementClick
                        )
                    }
                }

            }
        }
    }
}