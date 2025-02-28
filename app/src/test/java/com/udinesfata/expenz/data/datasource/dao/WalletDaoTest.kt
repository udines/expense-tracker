package com.udinesfata.expenz.data.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.udinesfata.expenz.data.datasource.local.database.AppDatabase
import com.udinesfata.expenz.data.datasource.local.database.WalletDao
import com.udinesfata.expenz.data.model.local.WalletDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class WalletDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var walletDao: WalletDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        walletDao = db.walletDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private fun createSampleWallet(
        id: Int = 1,
        name: String = "Main Wallet",
        balance: Double = 1000.0,
        currency: String = "USD",
        isSynced: Boolean = false,
        syncOperation: String? = null
    ) = WalletDb(id, name, balance, currency, isSynced, syncOperation)

    @Test
    fun insertAndRetrieveWallet() = runTest {
        val wallet = createSampleWallet()
        walletDao.createWallet(wallet)

        val retrievedWallet = walletDao.getWallet(1)
        assertThat(retrievedWallet, `is`(notNullValue()))
        assertThat(retrievedWallet?.name, `is`("Main Wallet"))
        assertThat(retrievedWallet?.balance, `is`(1000.0))
    }

    @Test
    fun insertDuplicateWallet() = runTest {
        val wallet = createSampleWallet()
        val duplicate = createSampleWallet(name = "Savings Wallet")
        walletDao.createWallet(wallet)
        walletDao.createWallet(duplicate)

        val retrievedWallet = walletDao.getWallet(1)
        assertThat(retrievedWallet, `is`(notNullValue()))
        assertThat(retrievedWallet?.name, `is`("Savings Wallet"))
    }

    @Test
    fun retrieveWalletsByName() = runTest {
        val wallet1 = createSampleWallet(id = 1, name = "Main Wallet")
        val wallet2 = createSampleWallet(id = 2, name = "Savings Wallet")
        walletDao.createWallet(wallet1)
        walletDao.createWallet(wallet2)

        val wallets = walletDao.getWallets(name = "Main Wallet")
        assertThat(wallets.size, `is`(1))
        assertThat(wallets[0].name, `is`("Main Wallet"))

        val allWallets = walletDao.getWallets(name = null)
        assertThat(allWallets.size, `is`(2))
    }

    @Test
    fun retrieveWalletByName() = runTest {
        val wallet = createSampleWallet(name = "Crypto Wallet")
        walletDao.createWallet(wallet)

        val retrievedWallet = walletDao.getWalletByName("Crypto Wallet")
        assertThat(retrievedWallet, `is`(notNullValue()))
        assertThat(retrievedWallet?.name, `is`("Crypto Wallet"))
    }

    @Test
    fun updateWallet() = runTest {
        val wallet = createSampleWallet()
        walletDao.createWallet(wallet)

        val updatedWallet =
            createSampleWallet(name = "Updated Wallet", balance = 5000.0, syncOperation = "update")
        walletDao.updateWallet(updatedWallet)

        val retrievedWallet = walletDao.getWallet(1)
        assertThat(retrievedWallet?.name, `is`("Updated Wallet"))
        assertThat(retrievedWallet?.balance, `is`(5000.0))
        assertThat(retrievedWallet?.syncOperation, `is`("update"))
    }

    @Test
    fun deleteWallet() = runTest {
        val wallet = createSampleWallet()
        walletDao.createWallet(wallet)

        walletDao.deleteWallet(1)

        val retrievedWallet = walletDao.getWallet(1)
        assertThat(retrievedWallet, `is`(nullValue()))
    }
}
