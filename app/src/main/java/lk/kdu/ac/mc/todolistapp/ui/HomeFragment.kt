package lk.kdu.ac.mc.todolistapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import lk.kdu.ac.mc.todolistapp.R
import lk.kdu.ac.mc.todolistapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Fill in the formatted strings
        val name = getString(R.string.developer_name)
        val id   = getString(R.string.student_id)
        binding.tvWelcome.text = getString(R.string.welcome_message, name)
        binding.tvDeveloperInfo.text = getString(R.string.developer_info, name, id)

        // Navigate on button click
        binding.btnGoToLists.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToLists())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
