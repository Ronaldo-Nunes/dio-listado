package br.com.runes.listado.data.dataSource

import br.com.runes.listado.data.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {
    suspend fun save(task: Task)
    suspend fun delete(taskId: Int)
    fun getAll(): Flow<List<Task>?>
}