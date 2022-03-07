package br.com.runes.listado.data.repository

import androidx.lifecycle.asLiveData
import br.com.runes.listado.data.dataSource.TaskDataSource
import br.com.runes.listado.data.mapper.DataMapper.toEntity
import br.com.runes.listado.data.mapper.DataMapper.toListModel
import br.com.runes.listado.domain.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class TaskRepository(private val dataSource: TaskDataSource) {
    fun getAllTasks() = dataSource.getAll().map { task ->
        task.toListModel()
    }.asLiveData(Dispatchers.IO)

    suspend fun saveTask(task: Task) = dataSource.save(task.toEntity())

    suspend fun deleteTask(task: Task) = dataSource.delete(taskId = task.id)
}