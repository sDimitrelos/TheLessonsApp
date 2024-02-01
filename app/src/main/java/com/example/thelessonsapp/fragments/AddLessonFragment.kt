package com.example.thelessonsapp.fragments

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
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.thelessonsapp.MainActivity
import com.example.thelessonsapp.R
import com.example.thelessonsapp.databinding.FragmentAddLessonBinding
import com.example.thelessonsapp.model.Lesson
import com.example.thelessonsapp.viewmodel.LessonViewModel


class AddLessonFragment : Fragment(R.layout.fragment_add_lesson), MenuProvider {

    private var addLessonBinding: FragmentAddLessonBinding? = null
    private val binding get() = addLessonBinding!!

    private lateinit var lessonsViewModel: LessonViewModel
    private lateinit var addLessonView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addLessonBinding = FragmentAddLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        lessonsViewModel = (activity as MainActivity).lessonViewModel
        addLessonView = view
    }

    private fun saveLesson(view: View) {
        val lessonTitle = binding.addLessonTitle.text.toString().trim()
        val lessonDesc = binding.addLessonDesc.text.toString().trim()

        if (lessonTitle.isNotEmpty()){
            val lesson = Lesson(0, lessonTitle, lessonDesc)
            lessonsViewModel.addLesson(lesson)

            Toast.makeText(addLessonView.context, "Lesson Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(addLessonView.context, "Please enter Lesson title", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_lesson, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveLesson(addLessonView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addLessonBinding = null
    }


}