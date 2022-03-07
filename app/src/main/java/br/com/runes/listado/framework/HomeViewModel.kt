package br.com.runes.listado.framework

import androidx.lifecycle.*
import br.com.runes.listado.domain.model.Task
import br.com.runes.listado.domain.useCases.DeleteTaskUseCase
import br.com.runes.listado.domain.useCases.LoadTasksUseCase
import br.com.runes.listado.domain.useCases.SaveTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    loadTasksUseCase: LoadTasksUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    val allTasks: LiveData<List<Task>?> = loadTasksUseCase()

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteTaskUseCase(task)
            }
        }
    }

    fun changeTaskStatus(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveTaskUseCase(task)
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val loadTasksUseCase: LoadTasksUseCase,
        private val saveTaskUseCase: SaveTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(loadTasksUseCase, saveTaskUseCase, deleteTaskUseCase) as T
            }
            throw IllegalArgumentException("ViewModel class not found!!")
        }
    }
}