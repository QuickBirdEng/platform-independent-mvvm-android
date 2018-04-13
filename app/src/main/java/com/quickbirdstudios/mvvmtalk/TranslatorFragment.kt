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
import com.quickbirdstudios.mvvmtalk.databinding.FragmentTranslatorBinding
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_translator.*

class TranslatorFragment : BaseFragment() {
    private val viewModel: TranslatorViewModel by viewModel()
    private lateinit var binding: FragmentTranslatorBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTranslatorBinding.inflate(inflater, container, false)
//        binding.viewModel = viewModel
        return binding.root
    }

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









    fun Subject<String>.receiveTextChangesFrom(editText: EditText) {
        RxTextView.textChanges(editText)
                .subscribe { newText -> this.onNext(newText.toString()) }
    }

    fun Subject<Unit>.receiveClicksFrom(view: View) {
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
