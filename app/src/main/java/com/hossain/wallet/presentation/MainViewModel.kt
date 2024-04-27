package com.hossain.wallet.presentation

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.hossain.wallet.data.local.WalletDatabase
import com.hossain.wallet.data.local.repository.OwedBillRepository
import com.hossain.wallet.data.local.repository.DebtBillRepository
import com.hossain.wallet.data.local.repository.ReceivedBillRepository
import com.hossain.wallet.data.local.repository.SpendBillRepository
import com.hossain.wallet.data.model.toBillStatement
import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.domain.model.toDebtBill
import com.hossain.wallet.domain.model.toOwedBill
import com.hossain.wallet.domain.model.toReceivedBill
import com.hossain.wallet.domain.model.toSpendBill
import com.hossain.wallet.utils.Constants
import com.hossain.wallet.utils.getRemainAmountPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val receivedBillRepository: ReceivedBillRepository,
    private val debtBillRepository: DebtBillRepository,
    private val spendBillRepository: SpendBillRepository,
    private val owedBillRepository: OwedBillRepository,
    ) : ViewModel() {

    var billType by mutableStateOf(BillType.CASH)
        private set
    var state by mutableStateOf(MainScreenState())
        private set

    private var _isSignIn by mutableStateOf(false)
    val isSignIn: Boolean = _isSignIn

    var recentStatement: MutableState<BillStatement?> = mutableStateOf(null)
        private set

    var dialogState by mutableStateOf(
        StatementDialogState(
            billType = billType,
            billCategory = state.billCategory,
        )
    )
        private set

    fun onDialogHeroMessageTextChange(value: String) {
        dialogState = dialogState.copy(
            heroMessage = value
        )
    }

    init {
        viewModelScope.launch {
            checkedSignIn()
        }
    }


    private suspend fun checkedSignIn() {
        App.create(Constants.APP_ID).currentUser?.let {
            WalletDatabase.configRealmDb()
            _isSignIn = true
            delay(5000)
            refreshStatementList()
            statisticUpdate()
        }
        _isSignIn = false
    }



    fun onDialogAmountTextChange(value: String) {
        var amount = 0
        try {
            amount = value.toInt()
        } catch (e: Exception) {
            println("amount String convert fail")
            dialogState = dialogState.copy(
                amount = 0
            )
            return
        }
        dialogState = dialogState.copy(
            amount = amount
        )
    }

    private fun statisticUpdate() {
        intent {
            val billType = this.billType
            val total = receivedBillRepository.getTotalAmountByType(billType)
            val spend = spendBillRepository.getTotalAmountByType(billType)
            val percentage = getRemainAmountPercentage(total, spend)
            println("percentage($spend / $total): $percentage")
            state = state.copy(
                totalAmount = total,
                spendAmount = spend,
                remainPercentage = percentage
            )
        }
    }

    private fun refreshStatementList(billCategory: BillCategory = BillCategory.SPEND) = intent {
        val billType = this.billType
        when (billCategory) {
            BillCategory.RECEIVED -> {
                val statementList = receivedBillRepository.getAllByBillType(billType)
                    .stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        it.toBillStatement()
                    }
                )
            }

            BillCategory.SPEND -> {
                val statementList = spendBillRepository.getAllByBillType(billType)
                    .stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        it.toBillStatement()
                    }
                )
            }

            BillCategory.DEBT -> {
                val statementList = debtBillRepository.getAllByBillType(billType)
                    .stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        it.toBillStatement()
                    }
                )
            }

            BillCategory.OWED -> {
                val statementList = owedBillRepository.getAllByBillType(billType)
                    .stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        it.toBillStatement()
                    }
                )
            }
        }
    }

    fun onDialogNoteTextChange(value: String) {
        dialogState = dialogState.copy(
            note = value
        )
    }

    fun onDialogChipBarChange(billType: BillType) {
        dialogState = dialogState.copy(
            billType = billType
        )
    }

    fun changeBillType(billType: BillType) {
        this.billType = billType
        refreshDialogState()
        refreshStatementList(state.billCategory)
        statisticUpdate()
    }

    fun changeBillCategory(billCategory: BillCategory) {
        state = state.copy(
            billCategory = billCategory
        )
        resetDialogState()
        refreshStatementList(state.billCategory)
    }

    fun onFabClicked() {
        changeShowDialogState(true)
    }

    fun onStatementClick(billStatement: BillStatement) {
        recentStatement.value = billStatement
        state = state.copy(
            showStatementDialog = true
        )
    }

    fun onCancelStatementDialog() {
        recentStatement.value = null
        state = state.copy(
            showStatementDialog = false
        )
        refreshStatementList()
        statisticUpdate()
    }

    fun onEditStatementDialog(billStatement: BillStatement) {
        dialogState = dialogState.copy(
            amount = billStatement.amount,
            id = billStatement.id,
            heroMessage = billStatement.title,
            note = billStatement.note,
            date = billStatement.date
        )
        state = state.copy(
            showStatementDialog = false,
            showDialog = true
        )
    }

    fun onDeleteStatementDialog(billStatement: BillStatement) = intent {
        when (state.billCategory) {
            BillCategory.RECEIVED -> {
                try {
                    receivedBillRepository.deleteBill(billStatement.toReceivedBill())
                } finally {
                    onCancelStatementDialog()
                }
            }

            BillCategory.SPEND -> {
                try {
                    spendBillRepository.deleteBill(billStatement.toSpendBill())
                } finally {
                    onCancelStatementDialog()
                }
            }

            BillCategory.DEBT -> {
                try {
                    debtBillRepository.deleteBill(billStatement.toDebtBill())
                } finally {
                    onCancelStatementDialog()
                }
            }

            BillCategory.OWED -> {
                try {
                    owedBillRepository.deleteBill(billStatement.toOwedBill())
                } finally {
                    onCancelStatementDialog()
                }
            }
        }
    }

    private fun changeShowDialogState(value: Boolean) {
        state = state.copy(
            showDialog = value
        )
    }

    fun onDialogCancel() {
        changeShowDialogState(false)
        resetDialogState()
    }

    fun onDialogSave() {
        if (dialogState.amount <= 0) return
        if (dialogState.heroMessage.isBlank()) return
        when (dialogState.billCategory) {
            BillCategory.RECEIVED -> {
                intent {
                    receivedBillRepository.upsert(dialogState.toReceivedBill())
                    saveDoneRefreshState()
                }
            }

            BillCategory.SPEND -> {
                intent {
                    spendBillRepository.upsert(dialogState.toSpendBill())
                    saveDoneRefreshState()
                }
            }

            BillCategory.DEBT -> {
                intent {
                    debtBillRepository.upsert(dialogState.toDebtBill())
                    saveDoneRefreshState()
                }
            }

            BillCategory.OWED -> {
                intent {
                    owedBillRepository.upsert(dialogState.toOwedBill())
                    saveDoneRefreshState()
                }
            }
        }

    }

    private fun saveDoneRefreshState() {
        onDialogCancel()
        refreshStatementList(state.billCategory)
        statisticUpdate()
    }

    private fun refreshDialogState() {
        dialogState = dialogState.copy(
            billType = billType,
            billCategory = state.billCategory,
        )
    }

    private fun resetDialogState() {
        dialogState = StatementDialogState(
            billType = billType,
            billCategory = state.billCategory,
        )
    }


    private fun intent(transform: suspend () -> Unit) {
        viewModelScope.launch {
            transform()
        }
    }
}