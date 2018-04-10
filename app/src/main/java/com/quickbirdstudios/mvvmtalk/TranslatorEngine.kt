package com.quickbirdstudios.mvvmtalk


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
class TranslatorEngine {
    companion object {
        fun translateToGerman(english: String): String {
            return "$english translated"
        }
    }
}