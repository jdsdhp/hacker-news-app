package com.jesusd0897.hackernews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jesusd0897.hackernews.R
import com.jesusd0897.hackernews.databinding.FragmentStoryDetailBinding
import com.jesusd0897.hackernews.ui.view.StoryViewClient
import com.maxkeppeler.sheets.info.InfoSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryDetailFragment : Fragment() {

    private var _binding: FragmentStoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStoryDetailBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.run {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            swipeLayout.setOnRefreshListener { refreshPage() }
            webView.run {
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                webViewClient = StoryViewClient(
                    onLoading = {
                        swipeLayout.isRefreshing = it
                    },
                    onError = { _, _, message ->
                        message?.let {
                            InfoSheet().show(requireActivity()) {
                                drawable(R.drawable.ic_twotone_error)
                                drawableColor(R.color.colorError)
                                title(R.string.error)
                                content(it)
                                displayNegativeButton(false)
                                onPositive(R.string.accept)
                            }
                        }
                    }
                )
                refreshPage()
            }
        }
    }

    private fun refreshPage() {
        binding.webView.loadUrl(requireArguments().getString("url")!!)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}