package lk.kdu.ac.mc.todolistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "todo_lists")
data class TodoList(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)