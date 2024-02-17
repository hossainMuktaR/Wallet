package com.hossain.wallet.presentation

import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillType

data class StatementDialogState(
    val id: Int? = null,
    val billType: BillType,
    val billCategory: BillCategory,
    val amount: Int = 0,
    val note: String? = null,
    val heroMessage: String = "",
    val date: Long? = null
)

fun StatementDialogState.toReceivedBill(): ReceivedBill {
    return ReceivedBill(
        id = this.id?: 0,
        type = this.billType,
        amount = this.amount,
        date = this.date?: System.currentTimeMillis(),
        receivedFrom = this.heroMessage,
        note = this.note
    )
}
fun StatementDialogState.toSpendBill(): SpendBill {
    return SpendBill(
        id = this.id?: 0,
        type = this.billType,
        amount = this.amount,
        date = this.date?: System.currentTimeMillis(),
        costFactor = this.heroMessage,
        note = this.note
    )
}
fun StatementDialogState.toDebtBill(): DebtBill {
    return DebtBill(
        id = this.id?: 0,
        type = this.billType,
        amount = this.amount,
        date = this.date?: System.currentTimeMillis(),
        deptTo = this.heroMessage,
        note = this.note
    )
}
fun StatementDialogState.toOwedBill(): OwedBill {
    return OwedBill(
        id = this.id?: 0,
        type = this.billType,
        amount = this.amount,
        date = this.date?: System.currentTimeMillis(),
        owedFrom = this.heroMessage,
        note = this.note
    )
}