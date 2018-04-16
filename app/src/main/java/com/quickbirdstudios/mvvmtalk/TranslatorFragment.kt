package com.quickbirdstudios.mvvmtalk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_translator.*

class TranslatorFragment : BaseFragment() {
    private val viewModel: TranslatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_translator, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //   *** supply inputs ***

        viewModel.input.englishText.receiveTextChangesFrom(englishInputEditText)
        viewModel.input.saveTrigger.receiveClicksFrom(saveTextButton)

        //   *** subscribe to outputs ***

        viewModel.output.germanText
                .subscribe { germanOutputEditText.text = it }

        viewModel.output.isSavingAllowed
                .subscribe { saveTextButton.isEnabled = it }

        viewModel.output.savedGermanTranslation
                .subscribe { germanText ->
                    showMessage("Saved to clipboard")

                    germanText.saveToClipboard()
                }
    }


    private fun Subject<String>.receiveTextChangesFrom(editText: EditText) {
        RxTextView.textChanges(editText)
                .subscribe { newText -> this.onNext(newText.toString()) }
    }

    private fun Subject<Unit>.receiveClicksFrom(view: View) {
        RxView.clicks(view)
                .subscribe { this.onNext(Unit) }
    }

    private fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun String.saveToClipboard() {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("translated text", this)
        clipboard.primaryClip = clip
    }
}
