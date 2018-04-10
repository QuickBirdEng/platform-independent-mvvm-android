package com.quickbirdstudios.mvvmtalk

import com.quickbirdstudios.quickboot.architecture.QuickRxViewModel
import com.quickbirdstudios.rx.databinding.BindableBoolean
import com.quickbirdstudios.rx.databinding.BindableField
import com.quickbirdstudios.rx.databinding.RxTrigger
import com.quickbirdstudios.rx.databinding.toBindable
import com.quickbirdstudios.rx.extension.filterNotNull
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
open class TranslatorViewModel :
        QuickRxViewModel<TranslatorViewModel.Inputs, TranslatorViewModel.Outputs>() {
    interface Inputs {
        val englishTextInput: BindableField<String>
        val saveTriggerInput: RxTrigger
    }

    interface Outputs {
        val germanTextOutput: BindableField<String>
        val isSavingAllowedOutput: BindableBoolean
        val saveTranslationActionOutput: Observable<String>
    }
}

class TranslatorViewModelImpl @Inject constructor() :
        TranslatorViewModel(),
        TranslatorViewModel.Inputs,
        TranslatorViewModel.Outputs {
    //   *** inputs ***

    override val saveTriggerInput = RxTrigger()

    override val englishTextInput = BindableField.just("")

    //   *** outputs ***

    override val germanTextOutput = englishTextInput.observe()
            .map { englishText -> TranslatorEngine.translateToGerman(englishText) }
            .toBindable()

    override val isSavingAllowedOutput = englishTextInput.observe()
            .map { englishText -> !englishText.isBlank() }
            .toBindable()

    override val saveTranslationActionOutput =
            Observables.combineLatest(germanTextOutput.observe(), saveTriggerInput.observe()) { english, _ -> english }
}

fun <T : Any> BindableField<T>.observe(): Observable<T> = this.toObservable().filterNotNull()
fun RxTrigger.observe(): Observable<Unit> = this.toObservable()

//    TODO NOW  ask sebi: how to most easily inject viewmodel

//    TODO NOW ask sebi: how would we save the "saved" state as a boolean state in the viewmodel

//    TODO NOW one example with filter or other operator would be great

//    TODO NOW saving should also be shown -> it is a side effect (maybe show but show in chapter "side effects"?
