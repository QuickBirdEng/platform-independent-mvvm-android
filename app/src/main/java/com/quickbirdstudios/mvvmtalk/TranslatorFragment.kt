package com.quickbirdstudios.mvvmtalk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.quickbirdstudios.mvvmtalk.databinding.FragmentTranslatorBinding
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

        RxTextView.textChanges(englishInputEditText)
                .subscribe { newText -> viewModel.input.englishText.onNext(newText.toString()) }

        RxView.clicks(saveTextButton)
                .subscribe { viewModel.input.saveTrigger.onNext(Unit) }

        //   *** subscribe to outputs ***

        viewModel.output.germanText
                .subscribe { germanText -> germanOutputEditText.text = germanText }

        viewModel.output.isSavingAllowed
                .subscribe { isSavingAllowed -> saveTextButton.isEnabled = isSavingAllowed }

        viewModel.output.savedGermanTranslation
                .subscribe { germanText ->
                    showMessage("Saved to clipboard")

                    germanText.saveToClipboard()
                }
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
