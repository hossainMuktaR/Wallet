package com.hossain.wallet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill

@Entity(
    tableName = "lendbill"
)
data class LendBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    @ColumnInfo(name = "dept_person")
    val deptPerson: String,
    val date: Long,
    val note: String?
): Bill
