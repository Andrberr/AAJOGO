package com.aajogo.jogo.sure.ui.game.exit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aajogo.jogo.sure.databinding.FragmentExitBinding

class ExitFragment : Fragment() {

    private var _binding: FragmentExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setClickListeners()
    }

    private fun setClickListeners() {
        with(binding) {
            yesButton.setOnClickListener {
                activity?.finish()
            }
            noButton.setOnClickListener {
                navigateToMenu()
            }
        }
    }

    private fun navigateToMenu() {
        val action = ExitFragmentDirections.actionExitFragmentToMenuFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}