package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hossain.wallet.data.model.BorrowBill
import com.hossain.wallet.data.model.SpendBill
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendBillDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(spendBill: SpendBill)

    @Update
    suspend fun update(spendBill: SpendBill)

    @Delete
    suspend fun delete(spendBill: SpendBill)

    @Query("SELECT * FROM spendbill ORDER BY date DESC")
    fun getAll(): Flow<List<SpendBill>>

    @Query("SELECT * FROM spendbill WHERE id = :id")
    fun getById(id: Int): Flow<SpendBill>
}