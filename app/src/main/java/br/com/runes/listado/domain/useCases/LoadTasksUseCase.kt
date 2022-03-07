package br.com.runes.listado.domain.useCases

import br.com.runes.listado.data.repository.TaskRepository

class LoadTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke() = repository.getAllTasks()
}