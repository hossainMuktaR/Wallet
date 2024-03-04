package com.hossain.wallet.data.local.dao

import com.hossain.wallet.data.model.OwedBill
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface OwedBillDao{

    suspend fun upsert(owedBill: OwedBill)

    suspend fun delete(owedBill: OwedBill)

    fun getAll(): Flow<List<OwedBill>>

    fun getAllByBillType(billType: String): Flow<List<OwedBill>>

    fun getById(id: ObjectId): Flow<OwedBill>
}