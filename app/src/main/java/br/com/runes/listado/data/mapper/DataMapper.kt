package br.com.runes.listado.data.mapper

import br.com.runes.listado.domain.model.Task
import br.com.runes.listado.data.entity.Task as TaskEntity

object DataMapper {

    fun TaskEntity.toModel(): Task {
        return Task(
            id = id,
            title = title,
            note = note,
            isComplete = isComplete,
            hour = hour,
            date = date
        )
    }

    fun Task.toEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            title = title,
            note = note,
            isComplete = isComplete,
            hour = hour,
            date = date
        )
    }

    fun List<TaskEntity>?.toListModel(): List<Task>? {
        return this?.map { task -> task.toModel() }
    }

}