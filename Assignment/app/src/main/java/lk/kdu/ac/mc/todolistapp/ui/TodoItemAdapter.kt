package lk.kdu.ac.mc.todolistapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lk.kdu.ac.mc.todolistapp.data.TodoItem
import lk.kdu.ac.mc.todolistapp.R

class TodoItemAdapter(
    val onDelete: (TodoItem) -> Unit,
    private val onEdit:   (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoItemAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // 1. Inflate the CardView root
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo_item, parent, false)
        return ItemViewHolder(view, onDelete, onEdit)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(
        itemView: View,
        private val onDelete: (TodoItem) -> Unit,
        private val onEdit:   (TodoItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        // 2. Find the inner TextView
        private val text: TextView = itemView.findViewById(R.id.textViewItemText)
        private var currentItem: TodoItem? = null

        init {
            text.setOnClickListener {
                currentItem?.let(onEdit)
            }
            text.setOnLongClickListener {
                currentItem?.let(onDelete)
                true
            }
        }

        fun bind(item: TodoItem) {
            currentItem = item
            text.text = item.text
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(a: TodoItem, b: TodoItem) = a.id == b.id
        override fun areContentsTheSame(a: TodoItem, b: TodoItem) = a == b
    }
}
