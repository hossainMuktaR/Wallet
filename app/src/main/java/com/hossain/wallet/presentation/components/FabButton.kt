package com.hossain.wallet.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun FabButton(
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit,
) {
    FloatingActionButton(
        onClick = onFabClicked,
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .border(5.dp, MaterialTheme.colorScheme.background, CircleShape),
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Image(imageVector = Icons.Default.Add, contentDescription = "add button",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary))
    }
}