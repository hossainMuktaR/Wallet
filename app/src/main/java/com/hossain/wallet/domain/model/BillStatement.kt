package com.hossain.wallet.domain.model

import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill

data class BillStatement(
    val id : Int,
    val amount: Int,
    val date: Long,
    val title: String,
    val type: BillType,
    val note: String?
): Bill

fun BillStatement.toReceivedBill(): ReceivedBill {
    return ReceivedBill(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        receivedFrom = this.title,
        note = this.note
    )
}
fun BillStatement.toSpendBill(): SpendBill {
    return SpendBill(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        costFactor = this.title,
        note = this.note
    )
}
fun BillStatement.toDebtBill(): DebtBill {
    return DebtBill(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        deptTo = this.title,
        note = this.note
    )
}
fun BillStatement.toOwedBill(): OwedBill {
    return OwedBill(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        owedFrom = this.title,
        note = this.note
    )
}

