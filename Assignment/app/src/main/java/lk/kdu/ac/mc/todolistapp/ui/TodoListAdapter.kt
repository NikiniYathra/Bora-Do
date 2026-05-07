package lk.kdu.ac.mc.todolistapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lk.kdu.ac.mc.todolistapp.data.TodoList
import lk.kdu.ac.mc.todolistapp.R

class TodoListAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<TodoList, TodoListAdapter.ListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // 1. Inflate the CardView root
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo_list, parent, false)
        return ListViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder(
        itemView: View,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        // 2. Find the inner TextView by its ID
        private val text: TextView = itemView.findViewById(R.id.textViewListName)
        private var currentListId: String? = null

        init {
            itemView.setOnClickListener {
                currentListId?.let(onClick)
            }
        }

        fun bind(list: TodoList) {
            currentListId = list.id
            text.text = list.name
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TodoList>() {
        override fun areItemsTheSame(a: TodoList, b: TodoList) = a.id == b.id
        override fun areContentsTheSame(a: TodoList, b: TodoList) = a == b
    }
}
