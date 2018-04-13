package com.quickbirdstudios.mvvmtalk

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
interface TranslatorViewModelInput {
    val englishText: PublishSubject<String>
    val saveTrigger: PublishSubject<Unit>
}

interface TranslatorViewModelOutput {
    val germanText: Observable<String>
    val isSavingAllowed: Observable<Boolean>
    val savedGermanTranslation: Observable<String>
}

abstract class TranslatorViewModel : ViewModel() {
    abstract val input: TranslatorViewModelInput
    abstract val output: TranslatorViewModelOutput
}

class TranslatorViewModelImpl @Inject constructor() :
        TranslatorViewModel(),
        TranslatorViewModelInput,
        TranslatorViewModelOutput {

    override val input = this
    override val output = this

    //   *** inputs ***

    override val englishText = PublishSubject.create<String>()

    override val saveTrigger = PublishSubject.create<Unit>()

    //   *** outputs ***

    override val germanText = input.englishText
            .map { TranslatorEngine.translateToGerman(it) }

    override val isSavingAllowed = input.englishText
            .map { !it.isEmpty() }

    override val savedGermanTranslation =
            Observables.combineLatest(output.germanText, input.saveTrigger)
                    .map { (german, _) -> german }
}
