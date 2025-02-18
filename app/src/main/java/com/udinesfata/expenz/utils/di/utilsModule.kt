package com.udinesfata.expenz.utils.di

import com.udinesfata.expenz.utils.ExceptionHandler
import org.koin.dsl.module

val utilsModule = module {
    single { ExceptionHandler() }
}