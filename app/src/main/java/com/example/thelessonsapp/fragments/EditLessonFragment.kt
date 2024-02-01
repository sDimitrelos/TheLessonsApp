package com.example.thelessonsapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thelessonsapp.MainActivity
import com.example.thelessonsapp.R
import com.example.thelessonsapp.databinding.FragmentEditLessonBinding
import com.example.thelessonsapp.model.Lesson
import com.example.thelessonsapp.viewmodel.LessonViewModel


class EditLessonFragment : Fragment(R.layout.fragment_edit_lesson), MenuProvider {

    private var editLessonBinding: FragmentEditLessonBinding? = null
    private val binding get() = editLessonBinding!!

    private lateinit var lessonsViewModel: LessonViewModel
    private lateinit var currentLesson:Lesson

    private val args: EditLessonFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editLessonBinding = FragmentEditLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        lessonsViewModel = (activity as MainActivity).lessonViewModel
        currentLesson = args.lesson!!

        binding.editLessonTitle.setText(currentLesson.lessonTile)
        binding.editLessonDesc.setText(currentLesson.lessonDesc)

        binding.editLessonFab.setOnClickListener{
            val lessonTitle = binding.editLessonTitle.text.toString().trim()
            val lessonDesc = binding.editLessonDesc.text.toString().trim()

            if(lessonTitle.isNotEmpty()){
                val lesson = Lesson(currentLesson.id, lessonTitle, lessonDesc)
                lessonsViewModel.updateLesson(lesson)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "Please enter title",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteLesson(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Lesson")
            setMessage("Do you want to delete this Lesson?")
            setPositiveButton("Delete"){_,_ ->
                lessonsViewModel.deleteLesson(currentLesson)
                Toast.makeText(context, "Lesson Deleted",Toast.LENGTH_SHORT)
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_lesson, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteLesson()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editLessonBinding = null
    }
}