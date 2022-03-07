package br.com.runes.listado.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.runes.listado.R
import br.com.runes.listado.TaskApp
import br.com.runes.listado.databinding.FragmentHomeBinding
import br.com.runes.listado.domain.model.Task
import br.com.runes.listado.domain.useCases.DeleteTaskUseCase
import br.com.runes.listado.domain.useCases.LoadTasksUseCase
import br.com.runes.listado.domain.useCases.SaveTaskUseCase
import br.com.runes.listado.framework.HomeViewModel
import br.com.runes.listado.ui.adapters.TaskListAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val listAdapter by lazy { TaskListAdapter() }
    private val viewModel: HomeViewModel by viewModels {
        val application = (requireActivity().application) as TaskApp
        val taskRepository = application.taskRepository
        HomeViewModel.Factory(
            LoadTasksUseCase(taskRepository),
            SaveTaskUseCase(taskRepository),
            DeleteTaskUseCase(taskRepository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initList()
        insertListeners()
        attachObservers()
    }

    private fun initList() {
        binding.rvTasks.apply {
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun showAddTaskFragment(task: Task? = null) {
        val directions = HomeFragmentDirections.navToAddTaskFragment(task)
        findNavController().navigate(directions)
    }

    private fun attachObservers() {
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            listAdapter.submitList(tasks)
        }
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            showAddTaskFragment()
        }

        listAdapter.listenerChangeStatus = { task ->
            viewModel.changeTaskStatus(task)
        }

        listAdapter.listenerEdit = { task ->
            showAddTaskFragment(task)
        }

        listAdapter.listenerDelete = { task ->
            viewModel.deleteTask(task)
        }
    }

}