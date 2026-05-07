package lk.kdu.ac.mc.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lk.kdu.ac.mc.todolistapp.data.RepositoryProvider
import lk.kdu.ac.mc.todolistapp.data.TodoList

class TodoListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryProvider.provideRepository(application)

    // Expose all lists as LiveData
    val allLists: LiveData<List<TodoList>> =
        repository.getAllLists().asLiveData()

    // For search results
    private val _searchQuery = MutableLiveData<String>("")
    val searchResults: LiveData<List<TodoList>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) repository.getAllLists().asLiveData()
        else repository.searchLists(query).asLiveData()
    }

    /** Update the search string to filter lists **/
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /** CRUD ops **/
    fun addList(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertList(TodoList(name = name))
    }

    fun updateList(list: TodoList) = viewModelScope.launch(Dispatchers.IO){
        repository.updateList(list)
    }

    fun deleteList(list: TodoList) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteList(list)
    }
}

// Factory if you ever need to pass params (not needed here):
class TodoListViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TodoListViewModel(application) as T
    }
}
