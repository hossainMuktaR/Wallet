package com.hossain.wallet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hossain.wallet.data.local.dao.BorrowBillDao
import com.hossain.wallet.data.local.dao.LendBillDao
import com.hossain.wallet.data.local.dao.ReceivedBillDao
import com.hossain.wallet.data.local.dao.SpendBillDao
import com.hossain.wallet.data.model.BorrowBill
import com.hossain.wallet.data.model.LendBill
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import kotlin.concurrent.Volatile

@Database(
    entities = [BorrowBill::class, LendBill::class, ReceivedBill::class, SpendBill::class],
    version = 1,
    exportSchema = false
)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun borrowBillDao(): BorrowBillDao
    abstract fun lendBillDao(): LendBillDao
    abstract fun receivedBillDao(): ReceivedBillDao
    abstract fun spendBillDao(): SpendBillDao

    companion object {
        @Volatile
        private var Instance: WalletDatabase? = null
        fun getWalletDatabase(context: Context): WalletDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    WalletDatabase::class.java,
                    "wallet_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}