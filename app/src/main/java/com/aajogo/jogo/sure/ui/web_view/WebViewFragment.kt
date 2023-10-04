package com.aajogo.jogo.sure.ui.web_view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.aajogo.jogo.sure.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            webView.webViewClient = WebViewClient()
            webView.webChromeClient = WebChromeClient()
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.loadUrl(args.url)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}