package com.aajogo.jogo.sure.ui.game.options

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aajogo.jogo.sure.R
import com.aajogo.jogo.sure.databinding.FragmentOptionsBinding
import com.aajogo.jogo.sure.ui.MainViewModel
import com.aajogo.jogo.sure.ui.game.MusicPlayer

class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initClickListeners()
    }

    private fun initViews() {
        with(binding) {
            setMode(soundOnOffView, viewModel.isSoundOn)
            setMode(musicOnOffView, viewModel.isMusicOn)
            setMode(vibrationOnOffView, viewModel.isVibrationOn)
        }
    }

    private fun initClickListeners() {
        with(binding) {
            menuIcon.setOnClickListener {
                navigateToMenu()
            }
            soundOnOffView.setOnClickListener {
                val isSoundOn = !viewModel.isSoundOn
                setMode(soundOnOffView, isSoundOn)
                viewModel.isSoundOn = isSoundOn
            }
            musicOnOffView.setOnClickListener {
                val isMusicOn = !viewModel.isMusicOn
                setMode(musicOnOffView, isMusicOn)
                viewModel.isMusicOn = isMusicOn
                if (isMusicOn) MusicPlayer.musicPlayer?.start()
                else MusicPlayer.musicPlayer?.pause()
            }
            vibrationOnOffView.setOnClickListener {
                val isVibrationOn = !viewModel.isVibrationOn
                setMode(vibrationOnOffView, isVibrationOn)
                viewModel.isVibrationOn = isVibrationOn
            }
        }
    }

    private fun setMode(view: ImageView, mode: Boolean) {
        if (mode) view.setBackgroundResource(R.drawable.on_mode)
        else view.setBackgroundResource(R.drawable.off_mode)
    }

    private fun navigateToMenu() {
        val action = OptionsFragmentDirections.actionOptionsFragmentToMenuFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}