package lk.kdu.ac.mc.todolistapp.data

import kotlinx.coroutines.flow.Flow

class TodoRepository internal constructor(
    private val listDao: TodoListDao,
    private val itemDao: TodoItemDao
) {
    // —— Lists ——
    fun getAllLists(): Flow<List<TodoList>> =
        listDao.getAllLists()

    suspend fun insertList(list: TodoList) =
        listDao.insert(list)

    suspend fun updateList(list: TodoList) =
        listDao.update(list)

    suspend fun deleteList(list: TodoList) =
        listDao.delete(list)

    fun searchLists(query: String): Flow<List<TodoList>> =
        listDao.searchLists(query)


    // —— Items ——
    fun getItemsForList(listId: String): Flow<List<TodoItem>> =
        itemDao.getItemsForList(listId)

    suspend fun insertItem(item: TodoItem) =
        itemDao.insert(item)

    suspend fun updateItem(item: TodoItem) =
        itemDao.update(item)

    suspend fun deleteItem(item: TodoItem) =
        itemDao.delete(item)

    suspend fun reorderItem(itemId: String, newOrder: Int) =
        itemDao.updateOrder(itemId, newOrder)

    fun searchItems(query: String): Flow<List<TodoItem>> =
        itemDao.searchItems(query)
}

// Optional helper to get a repo instance
object RepositoryProvider {
    fun provideRepository(context: android.content.Context): TodoRepository {
        val db = AppDatabase.getDatabase(context)
        return TodoRepository(db.todoListDao(), db.todoItemDao())
    }
}
