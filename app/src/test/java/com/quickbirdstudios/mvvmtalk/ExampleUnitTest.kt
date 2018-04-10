package com.quickbirdstudios.mvvmtalk

import android.util.Log
import io.reactivex.Observable
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val e = Observable.fromArray(1,2,3,4)
                .doOnNext { print(it) }
                .firstOrError()
                .blockingGet()

        print("Element $e")
    }
}
