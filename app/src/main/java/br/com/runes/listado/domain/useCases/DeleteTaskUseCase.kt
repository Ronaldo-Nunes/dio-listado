package br.com.runes.listado.domain.useCases

import br.com.runes.listado.data.repository.TaskRepository
import br.com.runes.listado.domain.model.Task

class DeleteTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.deleteTask(task)
}