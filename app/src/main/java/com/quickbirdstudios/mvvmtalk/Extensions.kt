package com.quickbirdstudios.mvvmtalk

import com.quickbirdstudios.rx.databinding.BindableField
import com.quickbirdstudios.rx.databinding.RxTrigger
import com.quickbirdstudios.rx.extension.filterNotNull
import io.reactivex.Observable


/**
 * Created by Malte Bucksch on 12/04/2018.
 */
fun <T : Any> BindableField<T>.asObservable(): Observable<T> = this.toObservable().filterNotNull()
fun RxTrigger.asObservable(): Observable<Unit> = this.toObservable()