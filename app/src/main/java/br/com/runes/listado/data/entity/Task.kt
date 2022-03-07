package br.com.runes.listado.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val isComplete: Boolean = false,
    val hour: String,
    val date: String
)
