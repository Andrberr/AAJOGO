package com.aajogo.jogo.sure.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aajogo.jogo.sure.databinding.FragmentSplashBinding
import com.aajogo.jogo.sure.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserver()
        viewModel.handleEvents(MainViewModel.ViewEvent.GetResponseFromUrl)
    }

    private fun initObserver() {
        viewModel.viewEffect.observe(viewLifecycleOwner) { effect ->
            if (effect is MainViewModel.ViewEffect.UrlResponse) {
                navigate(effect.response)
            } else {
                Toast.makeText(requireContext(), NETWORK_ERROR, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigate(response: String) {
        var action = SplashFragmentDirections.actionSplashFragmentToMenuFragment()
        if (response.contains(HTTP) || response.contains(HTTPS)) {
            val url = getUrlFromResponse(response)
            if (url != null) {
                action = SplashFragmentDirections.actionSplashFragmentToWebViewFragment(url)
            }
        }
        findNavController().navigate(action)
    }

    private fun getUrlFromResponse(response: String): String? {
        val pattern = Pattern.compile(REG_EXPR, Pattern.DOTALL)
        val matcher = pattern.matcher(response)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val HTTP = "http"
        private const val HTTPS = "https"
        private const val REG_EXPR = "<body>(.*?)</body>"
        private const val NETWORK_ERROR = "Network Error!"
    }
}