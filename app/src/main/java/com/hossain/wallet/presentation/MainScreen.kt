package com.hossain.wallet.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.presentation.components.BillTypeChipBar
import com.hossain.wallet.presentation.components.FabButton
import com.hossain.wallet.presentation.components.StatementDialog
import com.hossain.wallet.presentation.components.TopBar
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hossain.wallet.presentation.components.StatementDetails

@Composable
fun MainScreen(
    navController: NavController,
    vm: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val state = vm.state
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            var showStatement by remember {
                mutableStateOf(true)
            }
            TopBar()
            BillTypeChipBar(
                bill = vm.billType,
                onClick = {
                    vm.changeBillType(it)
                }
            )
            StatisticsCard(
                totalAmount = state.totalAmount,
                spendAmount = state.spendAmount,
                percentage = state.remainPercentage,
                onCardClick = {
                    if (showStatement) {
                        showStatement = false
                        vm.changeBillCategory(BillCategory.RECEIVED)
                    } else {
                        showStatement = true
                        vm.changeBillCategory(BillCategory.SPEND)
                    }
                }
            )
            AnimatedContent(targetState = showStatement, label = "Statement switcher") {
                if (it) {
                    StatementSection(
                        state.billCategory,
                        state.statementList,
                        Modifier.padding(top = 8.dp),
                        onCategoryClick = vm::changeBillCategory,
                        onClick = vm::onStatementClick
                    )
                } else {
                    ReceivedStatement(
                        state.statementList,
                        onStatementClick = vm::onStatementClick
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = !state.showDialog,
            modifier = Modifier.align(Alignment.BottomCenter),
            exit = ExitTransition.None,
            enter = EnterTransition.None
        ) {
            FabButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                onFabClicked = vm::onFabClicked
            )
        }

        AnimatedVisibility(
            visible = state.showDialog,
            label = "Statement Dialog",
            enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.background.copy(
                            alpha = 0.7f
                        )
                    ),
                contentAlignment = Alignment.Center
            )
            {
                StatementDialog(
                    statementDialogState = vm.dialogState,
                    onChipBarClick = vm::onDialogChipBarChange,
                    onAmountChange = vm::onDialogAmountTextChange,
                    onHeroMessageChange = vm::onDialogHeroMessageTextChange,
                    onNoteChange = vm::onDialogNoteTextChange,
                    onCancel = vm::onDialogCancel,
                    onSave = vm::onDialogSave
                )
            }
        }

        AnimatedVisibility(
            visible = state.showStatementDialog,
            label = "Statement Dialog",
            enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.background.copy(
                            alpha = 0.7f
                        )
                    ),
                contentAlignment = Alignment.Center
            )
            {
                StatementDetails(
                    billStatement = vm.recentStatement.value ?: return@Box,
                    onCancelClick = vm::onCancelStatementDialog,
                    onDeleteClick = vm::onDeleteStatementDialog,
                    onEditClick = vm::onEditStatementDialog
                )
            }
        }

    }

}