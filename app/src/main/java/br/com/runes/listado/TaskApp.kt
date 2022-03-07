package br.com.runes.listado

import android.app.Application
import br.com.runes.listado.data.database.AppDatabase
import br.com.runes.listado.data.repository.TaskRepository
import br.com.runes.listado.implementation.TaskDataSourceImpl

class TaskApp : Application() {
    private val roomDatabase by lazy { AppDatabase.getDatabase(this@TaskApp) }

    val taskRepository by lazy {
        val taskDao = roomDatabase.taskDao()
        val dataSource = TaskDataSourceImpl(taskDao)
        TaskRepository(dataSource)
    }
}