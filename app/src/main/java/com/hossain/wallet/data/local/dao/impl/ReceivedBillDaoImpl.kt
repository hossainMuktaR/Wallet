package com.hossain.wallet.data.local.dao.impl

import com.hossain.wallet.data.local.dao.ReceivedBillDao
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class ReceivedBillDaoImpl(
    private val realm: Realm
) : ReceivedBillDao {
    override suspend fun upsert(receivedBill: ReceivedBill) {
        realm.write {
            copyToRealm(receivedBill, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun delete(receivedBill: ReceivedBill) {
        realm.write {
            val bill = query<ReceivedBill>(query = "_id == $0", receivedBill._id).first().find()
            bill?.let {
                delete(it)
            }
        }
    }

    override fun getAll(): Flow<List<ReceivedBill>> {
        return realm.query<ReceivedBill>().find().asFlow().map { it.list }
    }

    override fun getAllByBillType(billType: String): Flow<List<ReceivedBill>> {
        return realm.query<ReceivedBill>(query = "type == $0", billType).find().asFlow().map { it.list }
    }

    override fun getById(id: ObjectId): Flow<ReceivedBill> {
        return realm.query<ReceivedBill>(query = "_id == $0", id).find().asFlow().map { it.list.first() }
    }

    override suspend fun getTotalReceivedAmount(): Int {
        return realm.query<ReceivedBill>().find().sumOf { it.amount }
    }

    override suspend fun getTotalReceivedAmountByType(billType: String): Int {
        return realm.query<ReceivedBill>(query = "type == $0", billType).find().sumOf { it.amount }
    }
}