package com.quickbirdstudios.mvvmtalk

import com.quickbirdstudios.quickboot.architecture.QuickFragment
import com.quickbirdstudios.rx.DisposeBag


/**
 * Created by Malte Bucksch on 12/04/2018.
 */
open class BaseFragment: QuickFragment() {
    val disposeBag = DisposeBag.create()

    override fun onDestroy() {
        disposeBag.dispose()

        super.onDestroy()
    }
}