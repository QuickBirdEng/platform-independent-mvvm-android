package com.quickbirdstudios.mvvmtalk

import android.arch.lifecycle.ViewModel
import com.quickbirdstudios.rx.databinding.BindableBoolean
import com.quickbirdstudios.rx.databinding.BindableField
import com.quickbirdstudios.rx.databinding.RxTrigger
import com.quickbirdstudios.rx.databinding.toBindable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
interface TranslatorViewModelInput {
    val englishText: BindableField<String>
    val saveTrigger: RxTrigger
}

interface TranslatorViewModelOutput {
    val germanText: BindableField<String>
    val isSavingAllowed: BindableBoolean
    val saveGermanTranslation: BindableField<String>
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

    override val englishText = BindableField.just("")

    override val saveTrigger = RxTrigger()

    //   *** outputs ***

    override val germanText = input.englishText.asObservable()
            .map { TranslatorEngine.translateToGerman(it) }
            .toBindable()

    override val isSavingAllowed = input.englishText.asObservable()
            .map { !it.isEmpty() }
            .toBindable()

    override val saveGermanTranslation =
            Observables.combineLatest(output.germanText.asObservable(), input.saveTrigger.asObservable())
                    .map { (english, _) -> english }
                    .toBindable()

}
