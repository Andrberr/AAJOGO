package com.aajogo.jogo.sure.ui.game.menu

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aajogo.jogo.sure.R
import com.aajogo.jogo.sure.databinding.FragmentMenuBinding
import com.aajogo.jogo.sure.ui.game.MusicPlayer

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        if (MusicPlayer.musicPlayer == null) {
            MusicPlayer.musicPlayer = MediaPlayer.create(context, R.raw.kazino)
            MusicPlayer.musicPlayer?.start()
        }
    }

    private fun initViews() {
        with(binding) {
            startButton.setOnClickListener {
                navigateToGame()
            }
            optionsButton.setOnClickListener {
                navigateToOptions()
            }
            exitButton.setOnClickListener {
                navigateToExit()
            }
        }
    }

    private fun navigateToGame() {
        val action = MenuFragmentDirections.actionMenuFragmentToGameFragment()
        findNavController().navigate(action)
    }

    private fun navigateToOptions() {
        val action = MenuFragmentDirections.actionMenuFragmentToOptionsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToExit() {
        val action = MenuFragmentDirections.actionMenuFragmentToExitFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}