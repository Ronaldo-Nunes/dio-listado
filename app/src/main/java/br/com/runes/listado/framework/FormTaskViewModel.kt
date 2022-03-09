package br.com.runes.listado.framework

import androidx.lifecycle.*
import br.com.runes.listado.domain.model.Task
import br.com.runes.listado.domain.useCases.SaveTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormTaskViewModel(
    private val initTask: Task?,
    private val saveTaskUseCase: SaveTaskUseCase
) : ViewModel() {
    private var _isNewTask = MutableLiveData<Boolean>()
    val isNewTask: LiveData<Boolean> = _isNewTask

   init {
       _isNewTask.value = initTask == null
   }

    fun saveTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveTaskUseCase(task)
            }
        }
    }

    fun getInitTask() = initTask


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val task: Task?,
        private val saveTaskUseCase: SaveTaskUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FormTaskViewModel::class.java)) {
                return FormTaskViewModel(task, saveTaskUseCase) as T
            }
            throw IllegalArgumentException("ViewModel class not found!!")
        }
    }
}