package com.hossain.wallet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.domain.model.BillType

@Entity(
    tableName = "debtbill"
)
data class DebtBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    @ColumnInfo(name = "dept_person")
    val deptTo: String,
    val type: BillType,
    val date: Long,
    val note: String?
): Bill

fun DebtBill.toBillStatement(): BillStatement {
    return BillStatement(
        id = this.id,
        type = this.type,
        amount = this.amount,
        date = this.date,
        title = this.deptTo,
        note = this.note
    )
}