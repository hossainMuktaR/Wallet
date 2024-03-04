package com.hossain.wallet.data.local.dao


import com.hossain.wallet.data.model.SpendBill
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface SpendBillDao{

    suspend fun upsert(spendBill: SpendBill)

    suspend fun delete(spendBill: SpendBill)

    fun getAll(): Flow<List<SpendBill>>

    fun getAllByBillType(billType: String): Flow<List<SpendBill>>

    fun getById(id: ObjectId): Flow<SpendBill>

    suspend fun getTotalSpendAmount(): Int

    suspend fun getTotalSpendAmountByType(billType: String): Int
}