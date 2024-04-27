package com.hossain.wallet.data.local


import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import com.hossain.wallet.utils.Constants
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object WalletDatabase{
    private val user = App.create(Constants.APP_ID).currentUser
    var realm: Realm? = null
    fun configRealmDb(){
        if(user != null){
            val config = SyncConfiguration.Builder(
                user = App.create(Constants.APP_ID).currentUser!!,
                schema = setOf(
                    DebtBill::class,
                    OwedBill::class,
                    ReceivedBill::class,
                    SpendBill::class
                )
            ).initialSubscriptions{realm ->
                add(
                    realm.query<ReceivedBill>(
                        "ownerId == $0",
                        user.id
                    ),
                    "init subscribe"
                )
            }
                .waitForInitialRemoteData()
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(configuration = config)
        }
    }
}