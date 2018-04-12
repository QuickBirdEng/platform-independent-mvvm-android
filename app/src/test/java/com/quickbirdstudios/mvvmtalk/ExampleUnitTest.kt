package com.quickbirdstudios.mvvmtalk

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testTranslation() {
        val viewModel = TranslatorViewModel()

        viewModel.input.englishText.set("Dog")

        assertEquals(viewModel.output.germanText.get(),"Hund")
    }
}
