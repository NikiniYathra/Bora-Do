package lk.kdu.ac.mc.todolistapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Query("SELECT * FROM todo_lists")
    fun getAllLists(): Flow<List<TodoList>>

    // Removed 'suspend' so Room sees this as a void-returning Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: TodoList)

    @Update
    fun update(list: TodoList)

    @Delete
    fun delete(list: TodoList)

    @Query("SELECT * FROM todo_lists WHERE name LIKE '%' || :query || '%'")
    fun searchLists(query: String): Flow<List<TodoList>>
}
