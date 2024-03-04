package com.hossain.wallet.data.local.dao.impl

import com.hossain.wallet.data.local.dao.SpendBillDao
import com.hossain.wallet.data.model.SpendBill
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class SpendBillDaoImpl(
    private val realm: Realm
): SpendBillDao {
    override suspend fun upsert(spendBill: SpendBill) {
        realm.write {
            copyToRealm(spendBill, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun delete(spendBill: SpendBill) {
        realm.write {
            val bill = query<SpendBill>(query = "_id == $0", spendBill._id).first().find()
            bill?.let {
                delete(it)
            }
        }
    }

    override fun getAll(): Flow<List<SpendBill>> {
        return realm.query<SpendBill>().find().asFlow().map { it.list }
    }

    override fun getAllByBillType(billType: String): Flow<List<SpendBill>> {
        return realm.query<SpendBill>(query = "type == $0", billType).find().asFlow().map { it.list }
    }

    override fun getById(id: ObjectId): Flow<SpendBill> {
        return realm.query<SpendBill>(query = "_id == $0", id).find().asFlow().map { it.list.first() }
    }

    override suspend fun getTotalSpendAmount(): Int {
        return realm.query<SpendBill>().find().sumOf { it.amount }
    }

    override suspend fun getTotalSpendAmountByType(billType: String): Int {
        return realm.query<SpendBill>(query = "type == $0", billType).find().sumOf { it.amount }
    }
}