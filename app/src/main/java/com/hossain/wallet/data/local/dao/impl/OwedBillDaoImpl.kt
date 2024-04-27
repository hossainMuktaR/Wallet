package com.hossain.wallet.data.local.dao.impl

import com.hossain.wallet.data.local.WalletDatabase
import com.hossain.wallet.data.local.dao.OwedBillDao
import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.data.model.OwedBill
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class OwedBillDaoImpl(
) : OwedBillDao {

    private val realm = WalletDatabase.realm!!
    override suspend fun upsert(owedBill: OwedBill) {
        realm.write {
            copyToRealm(owedBill, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun delete(owedBill: OwedBill) {
        realm.write {
            val bill = query<OwedBill>(query = "_id == $0", owedBill._id).first().find()
            bill?.let {
                delete(it)
            }
        }
    }

    override fun getAll(): Flow<List<OwedBill>> {
        return realm.query<OwedBill>().find().asFlow().map { it.list }
    }

    override fun getAllByBillType(billType: String): Flow<List<OwedBill>> {
        return realm.query<OwedBill>(query = "type == $0", billType).find().asFlow().map { it.list }
    }

    override fun getById(id: ObjectId): Flow<OwedBill> {
        return realm.query<OwedBill>(query = "_id == $0", id).find().asFlow()
            .map { it.list.first() }
    }
}