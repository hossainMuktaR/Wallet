package com.hossain.wallet.data.local

import android.content.Context
import com.hossain.wallet.data.local.dao.impl.DebtBillDaoImpl
import com.hossain.wallet.data.local.dao.impl.OwedBillDaoImpl
import com.hossain.wallet.data.local.dao.impl.ReceivedBillDaoImpl
import com.hossain.wallet.data.local.dao.impl.SpendBillDaoImpl
import io.realm.kotlin.Realm
import kotlin.concurrent.Volatile

class WalletDatabase(
    private val realm : Realm
){
    val owedBillDao = OwedBillDaoImpl(realm)
    val debtBillDao = DebtBillDaoImpl(realm)
    val receivedBillDao = ReceivedBillDaoImpl(realm)
    val spendBillDao = SpendBillDaoImpl(realm)
}