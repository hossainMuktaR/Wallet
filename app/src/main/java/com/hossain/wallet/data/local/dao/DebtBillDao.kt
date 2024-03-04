package com.hossain.wallet.data.local.dao

import com.hossain.wallet.data.model.DebtBill
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface DebtBillDao{

    suspend fun upsert(deptBill: DebtBill)

    suspend fun delete(deptBill: DebtBill)

    fun getAll(): Flow<List<DebtBill>>

    fun getAllByBillType(billType: String): Flow<List<DebtBill>>

    fun getById(id: ObjectId): Flow<DebtBill>
}