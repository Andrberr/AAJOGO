package com.aajogo.jogo.sure.ui.game.spin

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.aajogo.jogo.sure.R
import com.aajogo.jogo.sure.databinding.FragmentGameBinding
import com.aajogo.jogo.sure.ui.MainViewModel
import com.aajogo.jogo.sure.ui.game.MusicPlayer


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    private val spinAdapter1 = SpinAdapter()
    private val spinAdapter2 = SpinAdapter()
    private val spinAdapter3 = SpinAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initClickListeners()
        initPlayer()
    }

    private fun initViews() {
        with(binding) {
            balanceView.text = viewModel.balance.toString()
            betView.text = viewModel.betSize.toString()
            initRecyclers()
        }
    }

    private fun initClickListeners() {
        with(binding) {
            menuIcon.setOnClickListener {
                navigateToMenu()
            }
            settingsIcon.setOnClickListener {
                navigateToSettings()
            }
            minusButton.setOnClickListener {
                if (viewModel.betSize > 0) {
                    viewModel.betSize -= BET_SIZE
                    betView.text = viewModel.betSize.toString()
                }
            }
            plusButton.setOnClickListener {
                if (viewModel.betSize < viewModel.balance) {
                    viewModel.betSize += BET_SIZE
                    betView.text = viewModel.betSize.toString()
                }
            }
            winBg.setOnClickListener {
                winBg.isVisible = false
            }
            spinButton.setOnClickListener {
                if (checkBalance()) {
                    spinFrame.isVisible = false
                    initRecyclers()
                    scrollRecyclers()
                }
            }
        }
    }

    private fun initRecyclers() {
        with(binding) {
            initRecycler(spinRecycler1, spinAdapter1, FIRST_ID)
            initRecycler(spinRecycler2, spinAdapter2, SECOND_ID)
            (spinRecycler2.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                spinAdapter2.itemCount - 1,
                0
            )
            initRecycler(spinRecycler3, spinAdapter3, THIRD_ID)
        }
    }

    private fun initRecycler(recyclerView: RecyclerView, spinAdapter: SpinAdapter, id: Int) {
        recyclerView.apply {
            adapter = spinAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        recyclerView.suppressLayout(true)
        spinAdapter.setItems(createListItems(id))
    }

    private fun createListItems(id: Int): List<Int> {
        val isWin = viewModel.win == WIN
        return CreateSpinList.createList(isWin, id)
    }

    private fun scrollRecyclers() {
        with(binding) {
            scrollRecycler(spinRecycler1, spinAdapter1)
            addScrollListener()
            scrollRecycler(spinRecycler2, spinAdapter2)
            scrollRecycler(spinRecycler3, spinAdapter3)
        }
    }

    private fun scrollRecycler(spinRecycler: RecyclerView, spinAdapter: SpinAdapter) {
        val layoutManager = spinRecycler.layoutManager as? LinearLayoutManager
        val position = if (spinAdapter == spinAdapter2) {
            SCROLL_POSITION
        } else {
            spinAdapter.itemCount - SCROLL_POSITION
        }

        layoutManager?.startSmoothScroll(object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_END
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 0.5f
            }
        }.apply {
            targetPosition = position + 1
        })
    }

    private fun addScrollListener() {
        binding.spinRecycler1.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.spinFrame.isVisible = true
                    checkWin()
                    binding.spinRecycler1.removeOnScrollListener(this)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (viewModel.isSoundOn) MusicPlayer.soundPlayer?.start()
            }
        })
    }

    private fun checkWin() {
        if (viewModel.win == WIN) {
            binding.winBg.isVisible = true
            viewModel.balance += viewModel.betSize * WIN_INCREASE
            binding.balanceView.text = viewModel.balance.toString()
            viewModel.win = 0
            if (viewModel.isVibrationOn) vibrate()
        } else {
            viewModel.win++
        }
    }

    private fun checkBalance(): Boolean {
        return if (viewModel.balance >= viewModel.betSize) {
            viewModel.balance -= viewModel.betSize
            binding.balanceView.text = viewModel.balance.toString()
            true
        } else false
    }

    private fun initPlayer() {
        MusicPlayer.soundPlayer = MediaPlayer.create(context, R.raw.click_wheel)
    }

    private fun vibrate() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        VIBRATION_TIME,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(VIBRATION_TIME)
            }
        }

    }

    private fun navigateToMenu() {
        val action = GameFragmentDirections.actionGameFragmentToMenuFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSettings() {
        val action = GameFragmentDirections.actionGameFragmentToOptionsFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val START_BALANCE = 5000
        const val BET_SIZE = 100
        const val SCROLL_POSITION = 5
        const val WIN = 4
        const val FIRST_ID = 1
        const val SECOND_ID = 2
        const val THIRD_ID = 3
        const val ITEM_COUNT = 36
        const val WIN_INCREASE = 2
        const val VIBRATION_TIME = 1000L
    }
}