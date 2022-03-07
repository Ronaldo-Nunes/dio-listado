package br.com.runes.listado.implementation

import br.com.runes.listado.data.dao.TaskDAO
import br.com.runes.listado.data.dataSource.TaskDataSource
import br.com.runes.listado.data.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskDataSourceImpl(private val taskDAO: TaskDAO) : TaskDataSource {
    override suspend fun save(task: Task) {
        taskDAO.insert(task)
    }

    override suspend fun delete(taskId: Int) {
        taskDAO.delete(taskId)
    }

    override fun getAll(): Flow<List<Task>?> {
        return taskDAO.getAll()
    }
}