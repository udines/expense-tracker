package com.udinesfata.expenz

import com.udinesfata.expenz.data.di.dataModule
import com.udinesfata.expenz.domain.di.domainModule
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import kotlin.test.Test

class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        dataModule.verify()
        domainModule.verify()
    }
}