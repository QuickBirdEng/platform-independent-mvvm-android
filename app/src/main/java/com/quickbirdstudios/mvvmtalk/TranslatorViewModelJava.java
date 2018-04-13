package com.quickbirdstudios.mvvmtalk;

import android.arch.lifecycle.ViewModel;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by Malte Bucksch on 10/04/2018.
 */
interface TranslatorViewModelJavaInput {
    BehaviorSubject<String> getEnglishText();

    PublishSubject<Void> getSaveTrigger();
}

interface TranslatorViewModelJavaOutput {
    Observable<String> getGermanText();

    Observable<Boolean> isSavingAllowed();

    Observable<String> savedGermanTranslation();
}

abstract class TranslatorViewModelJava extends ViewModel {
    abstract TranslatorViewModelJavaInput getInput();

    abstract TranslatorViewModelJavaOutput getOutput();
}

class TranslatorViewModelJavaImpl
        extends TranslatorViewModelJava
        implements
        TranslatorViewModelJavaInput,
        TranslatorViewModelJavaOutput {
    @Override
    TranslatorViewModelJavaInput getInput() {
        return this;
    }

    @Override
    TranslatorViewModelJavaOutput getOutput() {
        return this;
    }

    @Override
    public BehaviorSubject<String> getEnglishText() {
        return BehaviorSubject.createDefault("");
    }

    @Override
    public PublishSubject<Void> getSaveTrigger() {
        return PublishSubject.create();
    }

    @Override
    public Observable<String> getGermanText() {
        return getInput()
                .getEnglishText()
                .map(TranslatorEngine.Companion::translateToGerman);
    }

    @Override
    public Observable<Boolean> isSavingAllowed() {
        return getInput()
                .getEnglishText()
                .map(english -> !english.isEmpty());
    }

    @Override
    public Observable<String> savedGermanTranslation() {
        return getInput().getSaveTrigger()
                .withLatestFrom(getOutput().getGermanText(), (trigger, germanText) -> germanText);
    }
}
