package com.udinesfata.expenz.utils

import kotlinx.coroutines.CoroutineExceptionHandler

class ExceptionHandler {
    val coroutine = CoroutineExceptionHandler { _, exception ->
        invoke(exception)
    }

    operator fun invoke(throwable: Throwable) {
        when (throwable) {
            is Error -> println("Error: ${throwable.message}")
            is Exception -> println("Exception: ${throwable.message}")
        }
    }
}