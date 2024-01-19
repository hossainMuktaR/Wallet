package com.hossain.wallet.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.hossain.wallet.data.model.LendBill
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WalletDatabaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WalletDatabase

    @Before
    fun setupDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WalletDatabase::class.java
        ).build()
    }
    @After
    fun closeDb() = database.close()

    @Test
    fun insertNoteAndGetById() = runTest {
        // GIVEN - Insert note
        val data = LendBill(
            id = 10,
            amount = 100,
            deptPerson = "Mine",
            date = 92846384346,
            note = "This is a Note"
        )
        database.lendBillDao().insert(data)

        // WHEN - Get the note by id
        val retriveNote = database.lendBillDao().getById(data.id).first()

        // THEN - the loaded data contains the expected values
        assertThat(retriveNote, notNullValue())
        assertThat(retriveNote.id, `is`(data.id))
        assertThat(retriveNote.amount, `is`(data.amount))
        assertThat(retriveNote.deptPerson, `is`(data.deptPerson))
    }
}