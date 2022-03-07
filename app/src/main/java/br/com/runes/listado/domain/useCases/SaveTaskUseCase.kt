package br.com.runes.listado.domain.useCases

import br.com.runes.listado.data.repository.TaskRepository
import br.com.runes.listado.domain.model.Task

class SaveTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.saveTask(task)
}