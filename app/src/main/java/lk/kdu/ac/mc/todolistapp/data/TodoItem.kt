package lk.kdu.ac.mc.todolistapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "todo_items",
    foreignKeys = [ForeignKey(
        entity = TodoList::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TodoItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val listId: String,
    val text: String,
    val order: Int = 0
)
