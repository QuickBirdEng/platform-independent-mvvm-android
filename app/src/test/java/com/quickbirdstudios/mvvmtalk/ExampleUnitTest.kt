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
        val viewModel: TranslatorViewModel = TranslatorViewModelImpl()

        viewModel.input.englishText.onNext("Dog")

        viewModel.output.germanText.test().assertValue("Dog translated")
    }
}
