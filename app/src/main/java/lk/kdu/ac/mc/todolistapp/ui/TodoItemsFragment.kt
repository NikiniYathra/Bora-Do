package lk.kdu.ac.mc.todolistapp.ui

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import lk.kdu.ac.mc.todolistapp.databinding.FragmentTodoItemsBinding
import lk.kdu.ac.mc.todolistapp.viewmodel.TodoItemViewModel
import lk.kdu.ac.mc.todolistapp.viewmodel.TodoItemViewModelFactory

class TodoItemsFragment : Fragment() {
    private var _binding: FragmentTodoItemsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<TodoItemsFragmentArgs>()
    private val vm: TodoItemViewModel by viewModels {
        TodoItemViewModelFactory(requireActivity().application, args.listId)
    }
    private lateinit var adapter: TodoItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentTodoItemsBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 1) Configure RecyclerView + adapter
        adapter = TodoItemAdapter(
            onDelete = { item ->
                // remove visually
                val newList = adapter.currentList.filter { it.id != item.id }
                adapter.submitList(newList)

                // delete in DB
                vm.deleteItem(item)

                // show Undo
                Snackbar.make(binding.root, "Deleted “${item.text}”", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        vm.addItem(item.text, item.order)
                    }
                    .show()
            },
            onEdit = { item ->
                // edit dialog
                val edit = EditText(requireContext()).apply {
                    setText(item.text)
                    inputType = InputType.TYPE_CLASS_TEXT
                }
                AlertDialog.Builder(requireContext())
                    .setTitle("Edit item")
                    .setView(edit)
                    .setPositiveButton("Save") { _, _ ->
                        val txt = edit.text.toString().trim()
                        if (txt.isNotEmpty() && txt != item.text) {
                            vm.updateItem(item.copy(text = txt))
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TodoItemsFragment.adapter
        }

        // 2) Drag & drop to reorder
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val item = adapter.currentList[vh.adapterPosition]
                adapter.onDelete(item)
            }
        }).attachToRecyclerView(binding.recyclerView)

        // 3) Search filtering
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String) = true
            override fun onQueryTextChange(newText: String): Boolean {
                vm.setSearchQuery(newText)
                return true
            }
        })

        // 4) Observe data
        vm.searchResults.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // 5) Add‐item FAB
        binding.fabAddItem.setOnClickListener {
            val edit = EditText(requireContext()).apply {
                hint = "Item description"
                inputType = InputType.TYPE_CLASS_TEXT
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Add new item")
                .setView(edit)
                .setPositiveButton("Add") { _, _ ->
                    edit.text.toString().takeIf { it.isNotBlank() }?.let {
                        vm.addItem(it)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
