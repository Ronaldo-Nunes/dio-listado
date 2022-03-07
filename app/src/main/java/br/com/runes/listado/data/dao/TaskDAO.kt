package br.com.runes.listado.data.dao

import androidx.room.*
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

    @Query("SELECT id, title, note, date, hour, isComplete FROM Tasks ORDER BY isComplete ASC, date ASC")
    fun getAll(): Flow<List<Task>?>
}