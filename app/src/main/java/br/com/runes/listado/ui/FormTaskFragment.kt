package br.com.runes.listado.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.runes.listado.R
import br.com.runes.listado.TaskApp
import br.com.runes.listado.databinding.FragmentFormTaskBinding
import br.com.runes.listado.domain.model.Task
import br.com.runes.listado.domain.useCases.SaveTaskUseCase
import br.com.runes.listado.extensions.format
import br.com.runes.listado.extensions.shortToast
import br.com.runes.listado.extensions.text
import br.com.runes.listado.framework.FormTaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class FormTaskFragment : Fragment(R.layout.fragment_form_task) {

    private lateinit var binding: FragmentFormTaskBinding
    private val navArgs by navArgs<FormTaskFragmentArgs>()
    private val viewModel: FormTaskViewModel by viewModels {
        val application = (requireActivity().application) as TaskApp
        val taskRepository = application.taskRepository
        FormTaskViewModel.Factory(navArgs.task, SaveTaskUseCase(taskRepository))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormTaskBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        insertListeners()
        fillForm()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            showDatePicker()
        }

        binding.tilHour.editText?.setOnClickListener {
            showTimePicker()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNewTask.setOnClickListener {
            saveTask()
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fillForm() {
        navArgs.task?.let {
            lifecycleScope.launchWhenCreated {
                binding.tilTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilNote.text = it.note
                binding.tilHour.text = it.hour
            }
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.addOnPositiveButtonClickListener {
            val timeZone = TimeZone.getDefault()
            val offset = timeZone.getOffset(Date().time) * -1
            binding.tilDate.text = Date(it + offset).format()
        }
        datePicker.show(childFragmentManager, "DATE_PICKER_TAG")
    }

    private fun showTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
            val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

            binding.tilHour.text = "$hour:$minute"
        }

        timePicker.show(childFragmentManager, null)
    }

    private fun saveTask() {
        val taskArg = navArgs.task

        val task = Task(
            title = binding.tilTitle.text,
            date = binding.tilDate.text,
            note = binding.tilNote.text,
            hour = binding.tilHour.text,
            isComplete = taskArg != null && taskArg.isComplete
        )
        if (task.isBlank()) {
            requireContext().shortToast(getString(R.string.message_fill_error))
        } else {
            viewModel.saveTask(task)
            findNavController().popBackStack()
        }
    }
}