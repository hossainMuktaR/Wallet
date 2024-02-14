package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.data.model.ReceivedBill
import kotlinx.coroutines.flow.Flow

@Dao
interface OwedBillDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(owedBill: OwedBill)

    @Update
    suspend fun update(owedBill: OwedBill)

    @Delete
    suspend fun delete(owedBill: OwedBill)

    @Query("SELECT * FROM owedbill ORDER BY date DESC")
    fun getAll(): Flow<List<OwedBill>>

    @Query("SELECT * FROM owedbill WHERE type = :billType ORDER BY date DESC")
    fun getAllByBillType(billType: String): Flow<List<OwedBill>>

    @Query("SELECT * FROM owedbill WHERE id = :id")
    fun getById(id: Int): Flow<OwedBill>
}