package com.jesusd0897.hackernews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.jesusd0897.hackernews.data.model.Story
import com.jesusd0897.hackernews.databinding.ItemStoryBinding
import com.jesusd0897.hackernews.util.DateHelper

class StoriesAdapter(
    private val onClickCallback: (story: Story) -> Unit
) : DragDropSwipeAdapter<Story, StoriesAdapter.StoryViewHolder>(emptyList()) {

    override fun getViewHolder(itemView: View) = StoryViewHolder(
        binding = ItemStoryBinding.inflate(LayoutInflater.from(itemView.context))
    )

    override fun getViewToTouchToStartDraggingItem(
        item: Story,
        viewHolder: StoryViewHolder,
        position: Int
    ): View = viewHolder.dragIcon

    override fun onBindViewHolder(item: Story, viewHolder: StoryViewHolder, position: Int) {
        viewHolder.bind(story = item)
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        DragDropSwipeAdapter.ViewHolder(binding.root) {

        val dragIcon = binding.dragIcon

        init {
            binding.cardView.setOnClickListener {
                onClickCallback.invoke(dataSet[bindingAdapterPosition])
            }
        }

        fun bind(story: Story) = binding.run {
            titleView.text =
                "${story.title}                                                                                                                                                       "
            subtitleView.text = "${story.author} - ${DateHelper.formatTime(story.createdAt)}"
        }

    }

}
