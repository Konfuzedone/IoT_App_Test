package com.example.iot_app_test.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iot_app_test.R
import com.example.iot_app_test.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var webView: WebView
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        webView = view.findViewById(R.id.webView)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        // Enable Javascript
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = false
        // Set a WebViewClient to handle onPageFinished event
        webView.webViewClient =
            object : WebViewClient() {
            /*override fun onPageFinished(view: WebView?, url: String?){
                // Page loading finished
            }*/
            }
        // Load the URL
        // Create an unencoded HTML string, then convert the unencoded HTML string into
        // bytes. Encode it with base64 and load the data.
        val unencodedHtml =
            "<html><body>'%23' is the percent code for ‘#‘ </body></html>"
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
        webView.loadData(encodedHtml, "text/html", "base64")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
