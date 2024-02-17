package com.hossain.wallet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.domain.model.BillType

@Entity(
    tableName = "spendbill"
)
data class SpendBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val date: Long,
    @ColumnInfo(name = "cost_factor")
    val costFactor: String,
    val type: BillType,
    val note: String?
): Bill

fun SpendBill.toBillStatement(): BillStatement {
    return BillStatement(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        title = this.costFactor,
        note = this.note
    )
}
