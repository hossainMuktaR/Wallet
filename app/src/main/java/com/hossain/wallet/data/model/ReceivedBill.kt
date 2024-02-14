package com.hossain.wallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillType

@Entity(
    tableName = "receivedbill"
)
data class ReceivedBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val date: Long,
    val source: String,
    val type: BillType,
    val note: String?
): Bill