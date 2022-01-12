package com.jesusd0897.hackernews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.jesusd0897.hackernews.R
import com.jesusd0897.hackernews.data.model.Story
import com.jesusd0897.hackernews.databinding.FragmentStoriesBinding
import com.jesusd0897.hackernews.ui.adapter.StoriesAdapter
import com.jesusd0897.hackernews.ui.view.gone
import com.jesusd0897.hackernews.ui.view.visible
import com.jesusd0897.hackernews.ui.viewmodel.StoriesViewModel
import com.maxkeppeler.sheets.info.InfoSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoriesFragment : Fragment() {

    private var _binding: FragmentStoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoriesViewModel by viewModels()

    private var loaderJob: Job? = null

    private val onItemSwipeListener = object : OnItemSwipeListener<Story> {
        override fun onItemSwiped(
            position: Int,
            direction: OnItemSwipeListener.SwipeDirection,
            item: Story
        ): Boolean {
            viewModel.deleteStory(story = item)
            Toast.makeText(requireContext(), R.string.item_deleted, Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStoriesBinding.inflate(inflater, container, false)
        initUI()
        subscribeUI()
        return binding.root
    }

    private fun initUI() {
        binding.run {
            swipeLayout.setOnRefreshListener { refresh() }
            recyclerView.run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                )
                orientation =
                    DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
                disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
                disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
                disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
                disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)
                adapter = StoriesAdapter(
                    onClickCallback = {
                        findNavController().navigate(
                            StoriesFragmentDirections.storiesFragmentToStoryDetailFragment(it.url)
                        )
                    }
                ).apply {
                    swipeListener = onItemSwipeListener
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun subscribeUI() {
        viewModel.run {
            isLoading.observe(viewLifecycleOwner) {
                binding.swipeLayout.isRefreshing = it
            }
            infoMessage.observe(viewLifecycleOwner) {
                it?.let {
                    InfoSheet().show(requireActivity()) {
                        title(R.string.information)
                        content(it)
                        displayNegativeButton(false)
                        onPositive(R.string.accept)
                    }
                }
            }
            errorMessage.observe(viewLifecycleOwner) {
                it?.let {
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
            stories.observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    binding.contentInfo.infoView.gone()
                    binding.recyclerView.run {
                        (adapter as StoriesAdapter).dataSet = it
                        visible()
                    }
                } else {
                    binding.run {
                        contentInfo.infoText.text = getString(R.string.no_data_to_show)
                        contentInfo.infoIcon.setImageResource(R.drawable.ic_notification_info)
                        contentInfo.infoView.visible()
                    }
                }

            }
        }
    }

    private fun refresh() {
        loaderJob?.cancel()
        loaderJob = lifecycleScope.launch { viewModel.fetchStories() }
    }


}