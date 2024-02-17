package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.ReceivedBill
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtBillDao{

    @Upsert
    suspend fun upsert(deptBill: DebtBill)

    @Delete
    suspend fun delete(deptBill: DebtBill)

    @Query("SELECT * FROM debtbill ORDER BY date DESC")
    fun getAll(): Flow<List<DebtBill>>

    @Query("SELECT * FROM debtbill WHERE type = :billType ORDER BY date DESC")
    fun getAllByBillType(billType: String): Flow<List<DebtBill>>

    @Query("SELECT * FROM debtbill WHERE id = :id")
    fun getById(id: Int): Flow<DebtBill>
}