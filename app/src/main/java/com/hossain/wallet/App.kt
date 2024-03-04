package com.hossain.wallet

import android.app.Application
import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class App : Application() {
    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        val config = RealmConfiguration.create(
            schema = setOf(
                DebtBill::class,
                OwedBill::class,
                ReceivedBill::class,
                SpendBill::class
            )
        )
        realm = Realm.open(configuration = config)
    }
}