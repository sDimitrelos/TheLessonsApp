package com.example.thelessonsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thelessonsapp.databinding.LessonLayoutBinding
import com.example.thelessonsapp.fragments.HomeFragmentDirections
import com.example.thelessonsapp.model.Lesson

class LessonAdapter : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>(){

    class LessonViewHolder(val itemBinding: LessonLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Lesson>(){
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id ==newItem.id &&
                    oldItem.lessonDesc == newItem.lessonDesc &&
                    oldItem.lessonTile == newItem.lessonTile
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(
            LessonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val currentLesson = differ.currentList[position]

        holder.itemBinding.lessonTitle.text = currentLesson.lessonTile
        holder.itemBinding.lessonDesc.text = currentLesson.lessonDesc

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditLessonFragment(currentLesson)
            it.findNavController().navigate(direction)
        }
    }
}