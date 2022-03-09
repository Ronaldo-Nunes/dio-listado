package br.com.runes.listado.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.runes.listado.databinding.DialogTaskDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogTaskDetail : BottomSheetDialogFragment() {
    private lateinit var binding: DialogTaskDetailBinding
    private val navArgs by navArgs<DialogTaskDetailArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTaskDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            task = navArgs.task

            ivClose.setOnClickListener {
                dismiss()
            }
        }
    }

}