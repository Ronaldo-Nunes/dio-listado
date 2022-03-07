package br.com.runes.listado.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.runes.listado.data.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM Tasks WHERE Tasks.id = :taskId")
    suspend fun delete(taskId: Int)

    @Query("DELETE FROM Tasks")
    suspend fun deleteAll()

    @Query("SELECT id, title, note, date, hour, isComplete FROM Tasks ORDER BY isComplete ASC, date DESC")
    fun getAll(): Flow<List<Task>?>
}