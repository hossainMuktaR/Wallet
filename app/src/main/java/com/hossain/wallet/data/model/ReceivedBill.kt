package com.hossain.wallet.data.model

import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillStatement
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.utils.Constants
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class ReceivedBill(): Bill, RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var ownerId: String = App.create(Constants.APP_ID).currentUser!!.id
    var amount: Int = 0
    var receivedFrom: String = ""
    var type: String = ""
    var date: Long = 0
    var note: String? = null
    constructor(
        _id: ObjectId = ObjectId(),
        amount: Int,
        receivedFrom: String,
        type: String,
        date: Long,
        note: String?
    ): this() {
        this._id = _id
        this.date = date
        this.note = note
        this.type = type
        this.receivedFrom = receivedFrom
        this.amount = amount
    }
}

fun ReceivedBill.toBillStatement(): BillStatement {
    return BillStatement(
        id = this._id,
        type = BillType.valueOf(this.type),
        amount = this.amount,
        date = this.date,
        title = this.receivedFrom,
        note = this.note
    )
}