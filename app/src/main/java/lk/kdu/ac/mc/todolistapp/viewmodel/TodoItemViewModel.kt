package lk.kdu.ac.mc.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lk.kdu.ac.mc.todolistapp.data.RepositoryProvider
import lk.kdu.ac.mc.todolistapp.data.TodoItem
import kotlinx.coroutines.Dispatchers

/**
 * ViewModel for a single list's items.
 * Requires the listId to scope queries.
 */
class TodoItemViewModel(
    application: Application,
    private val listId: String
) : AndroidViewModel(application) {
    private val repository = RepositoryProvider.provideRepository(application)

    // All items in this list
    val items: LiveData<List<TodoItem>> =
        repository.getItemsForList(listId).asLiveData()

    // Search over items in this list
    private val _searchQuery = MutableLiveData<String>("")
    val searchResults: LiveData<List<TodoItem>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) items
        else repository.searchItems(query).asLiveData()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /** CRUD **/
    fun addItem(text: String, order: Int = 0) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertItem(TodoItem(listId = listId, text = text, order = order))
    }

    fun updateItem(item: TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateItem(item)
    }

    fun deleteItem(item: TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteItem(item)
    }

    fun reorderItem(itemId: String, newOrder: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.reorderItem(itemId, newOrder)
    }
}

/** Factory so you can pass the listId into the VM **/
class TodoItemViewModelFactory(
    private val application: Application,
    private val listId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoItemViewModel(application, listId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
