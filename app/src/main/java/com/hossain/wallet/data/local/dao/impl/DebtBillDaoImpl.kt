package com.hossain.wallet.data.local.dao.impl

import com.hossain.wallet.data.local.WalletDatabase
import com.hossain.wallet.data.local.dao.DebtBillDao
import com.hossain.wallet.data.model.DebtBill
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class DebtBillDaoImpl(
) : DebtBillDao {

    private val realm = WalletDatabase.realm!!
    override suspend fun upsert(deptBill: DebtBill) {
        realm.write {
            copyToRealm(deptBill, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun delete(deptBill: DebtBill) {
        realm.write {
            val bill = query<DebtBill>(query = "_id == $0", deptBill._id).first().find()
            bill?.let {
                delete(it)
            }
        }
    }

    override fun getAll(): Flow<List<DebtBill>> {
        return realm.query<DebtBill>().find().asFlow().map { it.list }
    }

    override fun getAllByBillType(billType: String): Flow<List<DebtBill>> {
        return realm.query<DebtBill>(query = "type == $0", billType).find().asFlow().map { it.list }
    }

    override fun getById(id: ObjectId): Flow<DebtBill> {
        return realm.query<DebtBill>(query = "_id == $0", id).find().asFlow()
            .map { it.list.first() }
    }
}