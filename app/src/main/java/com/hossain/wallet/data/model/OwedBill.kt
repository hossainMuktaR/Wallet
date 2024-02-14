package com.hossain.wallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillType

@Entity(
    tableName = "owedbill"
)
data class OwedBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val from: String,
    val type: BillType,
    val date: Long,
    val note: String?
): Bill
