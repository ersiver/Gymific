package com.ersiver.gymific.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "DATA_LOADED"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow)
            countingIdlingResource.decrement()
    }

    inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
        increment()
        return try {
            function()
        } finally {
            decrement()
        }
    }
}