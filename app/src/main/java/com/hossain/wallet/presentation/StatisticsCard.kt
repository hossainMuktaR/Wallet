package com.hossain.wallet.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.hossain.wallet.utils.getRemainAmountPercentage

@Composable
fun StatisticsCard(
    totalAmount: Int,
    spendAmount: Int,
    percentage: Float?,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                 onCardClick()
            },
        shape = RoundedCornerShape(10.dp),
    ) {
        Row{
            // left side - usage graph
            LeftStatisticsGraph(
                percentage,
                Modifier.weight(1f)
            )
            // right side - text statistics
            RightStatisticsText(
                totalAmount,
                spendAmount,
                Modifier.weight(1f))
        }
    }
}

@Composable
fun LeftStatisticsGraph(
    percentage: Float?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val strokeColor = MaterialTheme.colorScheme.onBackground
        val percent = percentage ?: 1f
        Canvas(
            modifier = Modifier
                .fillMaxSize(0.7f)
                .padding(16.dp)
        ) {
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = 360f * percent,
                useCenter = false,
                style = Stroke(width = 32f, cap = StrokeCap.Round)

            )
        }
        Text(text = if(percentage == null) "Hi" else "${(percent*100).toInt()}%")
    }
}

@Composable
fun RightStatisticsText(
    totalAmount: Int,
    spendAmount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .padding(end = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Text(text = "Total: ")
            Text(text = "$totalAmount \u09F3")
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Text(text = "Spend: ")
            Text(text = "$spendAmount \u09F3")
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Text(text = "Balance: ")
            Text(text = "${totalAmount - spendAmount} \u09F3")
        }
    }
}