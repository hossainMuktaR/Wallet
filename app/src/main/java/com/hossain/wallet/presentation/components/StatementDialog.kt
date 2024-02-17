package com.hossain.wallet.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.presentation.StatementDialogState
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun StatementDialog(
    statementDialogState: StatementDialogState,
    modifier: Modifier = Modifier,
    onChipBarClick: (BillType) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    onAmountChange: (String) -> Unit,
    onHeroMessageChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val sourceFocusRequester = remember {
        FocusRequester()
    }
    val noteFocusRequester = remember {
        FocusRequester()
    }
    var heroMessageText by remember {
        mutableStateOf("Cost-Factor")
    }
    LaunchedEffect(key1 = statementDialogState) {
        when(statementDialogState.billCategory) {
            BillCategory.RECEIVED -> heroMessageText = "Received From"
            BillCategory.SPEND -> heroMessageText = "Cost-Factor"
            BillCategory.DEBT -> heroMessageText = "Debt to "
            BillCategory.OWED -> heroMessageText = "Owed from"
        }
    }
    Box(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BillTypeChipBar(
                bill = statementDialogState.billType,
                showOnDialog = true,
                onClick = onChipBarClick
            )
            // amount text field
            OutlinedTextField(
                value = if(statementDialogState.amount == 0) "" else statementDialogState.amount.toString(),
                onValueChange = {
                    onAmountChange(it)
                },
                label = {
                    Text("Enter Amount")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        sourceFocusRequester.requestFocus()
                    }
                )
            )
            // hero message text field
            OutlinedTextField(
                value = statementDialogState.heroMessage,
                onValueChange = { onHeroMessageChange(it) },
                label = {
                    Text(heroMessageText)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.clearFocus()
                        noteFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier.focusRequester(sourceFocusRequester)
            )
            // note text field
            OutlinedTextField(
                value = statementDialogState.note ?: "",
                onValueChange = {
                    onNoteChange(it)
                },
                label = {
                    Text("Note")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onSave()
                    }
                ),
                modifier = Modifier.focusRequester(noteFocusRequester)

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onCancel) {
                    Text("Cancel")
                }
                Button(onClick = onSave) {
                    Text(text = "Save")
                }
            }
        }
    }
}

