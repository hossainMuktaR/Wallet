package com.hossain.wallet.presentation.components

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillStatement
import java.util.Date

@Composable
fun SimpleStatement(
    statement: BillStatement,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = 16.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = Icons.Default.Star, contentDescription = "Statement Image")
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp)) {
            Text(statement.title)
            Text(statement.date.toStringDate())
        }
        Text("\u09F3 ${statement.amount}")
    }
    Spacer(modifier = Modifier.height(8.dp))
}

fun Long.toStringDate(): String = DateFormat.format("dd/MM/yyyy", Date(this)).toString()