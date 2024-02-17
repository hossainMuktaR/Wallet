package com.hossain.wallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.domain.model.BillType

@Entity(
    tableName = "receivedbill"
)
data class ReceivedBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val date: Long,
    val receivedFrom: String,
    val type: BillType,
    val note: String?
): Bill

fun ReceivedBill.toBillStatement(): BillStatement {
    return BillStatement(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        title = this.receivedFrom,
        note = this.note
    )
}