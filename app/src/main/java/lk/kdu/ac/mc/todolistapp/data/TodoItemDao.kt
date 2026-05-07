package lk.kdu.ac.mc.todolistapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todo_items WHERE listId = :listId ORDER BY `order`")
    fun getItemsForList(listId: String): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TodoItem)

    @Update
    fun update(item: TodoItem)

    @Delete
    fun delete(item: TodoItem)

    @Query("UPDATE todo_items SET `order` = :newOrder WHERE id = :itemId")
    fun updateOrder(itemId: String, newOrder: Int)

    @Query("SELECT * FROM todo_items WHERE text LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<TodoItem>>
}
