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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import lk.kdu.ac.mc.todolistapp.databinding.FragmentTodoListsBinding
import lk.kdu.ac.mc.todolistapp.viewmodel.TodoListViewModel
import lk.kdu.ac.mc.todolistapp.viewmodel.TodoListViewModelFactory

class TodoListsFragment : Fragment() {
    private var _binding: FragmentTodoListsBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<TodoListViewModel> {
        TodoListViewModelFactory(requireActivity().application)
    }
    private lateinit var adapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentTodoListsBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 1) Lists RecyclerView + adapter
        adapter = TodoListAdapter { listId ->
            val action = TodoListsFragmentDirections
                .actionListsToItems(listId)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TodoListsFragment.adapter
        }

        // 2) Search lists
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String) = true
            override fun onQueryTextChange(newText: String): Boolean {
                vm.setSearchQuery(newText)
                return true
            }
        })

        // 3) Observe lists data
        vm.searchResults.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // 4) Add‐list FAB
        binding.fabAddList.setOnClickListener {
            val edit = EditText(requireContext()).apply {
                hint = "List name"
                inputType = InputType.TYPE_CLASS_TEXT
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Create new list")
                .setView(edit)
                .setPositiveButton("Create") { _, _ ->
                    edit.text.toString().trim().takeIf { it.isNotEmpty() }?.let {
                        vm.addList(it)
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
