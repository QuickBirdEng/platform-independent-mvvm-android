package com.quickbirdstudios.mvvmtalk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.quickbirdstudios.mvvmtalk.databinding.FragmentTranslatorBinding
import com.quickbirdstudios.rx.extension.disposedBy

class TranslatorFragment : BaseFragment() {
    private val viewModel: TranslatorViewModel by viewModel()
    private lateinit var binding: FragmentTranslatorBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTranslatorBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.output.saveGermanTranslation
                .observe()
                .subscribe { englishText ->
                    Toast.makeText(activity, "Saved to clipboard", Toast.LENGTH_SHORT).show()

                    englishText.saveToClipboard()
                }
                .disposedBy(disposeBag)
    }

    private fun String.saveToClipboard() {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("translated text", this)
        clipboard.primaryClip = clip
    }
}
