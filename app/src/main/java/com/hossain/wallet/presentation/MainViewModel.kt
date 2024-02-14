package com.hossain.wallet.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hossain.wallet.data.local.WalletDatabase
import com.hossain.wallet.data.local.repository.OwedBillRepository
import com.hossain.wallet.data.local.repository.DebtBillRepository
import com.hossain.wallet.data.local.repository.ReceivedBillRepository
import com.hossain.wallet.data.local.repository.SpendBillRepository
import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.utils.getRemainAmountPercentage
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val context: Context
) : ViewModel() {
    private val walletDatabase = WalletDatabase.getWalletDatabase(context = context)
    private val receivedBillRepository = ReceivedBillRepository(walletDatabase.receivedBillDao())
    private val spendBillRepository = SpendBillRepository(walletDatabase.spendBillDao())
    private val deptBillRepository = DebtBillRepository(walletDatabase.debtBillDao())
    private val owedBillRepository = OwedBillRepository(walletDatabase.owedBillDao())

    var billType by mutableStateOf(BillType.CASH)
        private set
    var state by mutableStateOf(MainScreenState())
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
        refreshStatementList()
        statisticUpdate()
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
                val statementList = receivedBillRepository.getAllByBillType(billType).stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        BillStatement(
                            amount = it.amount,
                            date = it.date,
                            title = it.source,
                            type = it.type,
                            note = it.note
                        )
                    }
                )
            }

            BillCategory.SPEND -> {
                val statementList = spendBillRepository.getAllByBillType(billType).stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        BillStatement(
                            amount = it.amount,
                            date = it.date,
                            title = it.costFactor,
                            type = it.type,
                            note = it.note
                        )
                    }
                )
            }

            BillCategory.DEBT -> {
                val statementList = deptBillRepository.getAllByBillType(billType).stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        BillStatement(
                            amount = it.amount,
                            date = it.date,
                            title = it.deptPerson,
                            type = it.type,
                            note = it.note
                        )
                    }
                )
            }

            BillCategory.OWED -> {
                val statementList = owedBillRepository.getAllByBillType(billType).stateIn(viewModelScope).value.toList()
                state = state.copy(
                    statementList = statementList.map {
                        BillStatement(
                            amount = it.amount,
                            date = it.date,
                            title = it.from,
                            type = it.type,
                            note = it.note
                        )
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
        if (dialogState.heroMessage.isNullOrBlank()) return
        when (dialogState.billCategory) {
            BillCategory.RECEIVED -> {
                val receivedBill = ReceivedBill(
                    amount = dialogState.amount,
                    source = dialogState.heroMessage,
                    type = dialogState.billType,
                    note = dialogState.note,
                    date = System.currentTimeMillis()
                )
                intent {
                    receivedBillRepository.insertBill(receivedBill)
                    saveDoneRefreshState()
                }
            }

            BillCategory.SPEND -> {
                val spendBill = SpendBill(
                    amount = dialogState.amount,
                    costFactor = dialogState.heroMessage,
                    type = dialogState.billType,
                    note = dialogState.note,
                    date = System.currentTimeMillis()
                )
                intent {
                    spendBillRepository.insertBill(spendBill)
                    saveDoneRefreshState()
                }
            }

            BillCategory.DEBT -> {
                val deptBill = DebtBill(
                    amount = dialogState.amount,
                    deptPerson = dialogState.heroMessage,
                    type = dialogState.billType,
                    note = dialogState.note,
                    date = System.currentTimeMillis()
                )
                intent {
                    deptBillRepository.insertBill(deptBill)
                    saveDoneRefreshState()
                }
            }

            BillCategory.OWED -> {
                val owedBill = OwedBill(
                    amount = dialogState.amount,
                    from = dialogState.heroMessage,
                    type = dialogState.billType,
                    note = dialogState.note,
                    date = System.currentTimeMillis()
                )
                intent {
                    owedBillRepository.insertBill(owedBill)
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

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(context) as T
            }
        }
    }
}