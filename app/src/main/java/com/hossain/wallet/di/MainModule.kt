package com.hossain.wallet.di

import com.hossain.wallet.data.local.WalletDatabase
import com.hossain.wallet.data.local.dao.DebtBillDao
import com.hossain.wallet.data.local.dao.OwedBillDao
import com.hossain.wallet.data.local.dao.ReceivedBillDao
import com.hossain.wallet.data.local.dao.SpendBillDao
import com.hossain.wallet.data.local.dao.impl.DebtBillDaoImpl
import com.hossain.wallet.data.local.dao.impl.OwedBillDaoImpl
import com.hossain.wallet.data.local.dao.impl.ReceivedBillDaoImpl
import com.hossain.wallet.data.local.dao.impl.SpendBillDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideDebtBillDao(): DebtBillDao {
        return DebtBillDaoImpl()
    }
    @Provides
    @Singleton
    fun provideSpendBillDao(): SpendBillDao {
        return SpendBillDaoImpl()
    }
    @Provides
    @Singleton
    fun provideOwedBillDao(): OwedBillDao {
        return OwedBillDaoImpl()
    }
    @Provides
    @Singleton
    fun provideReceivedBillDao(): ReceivedBillDao {
        return ReceivedBillDaoImpl()
    }
}