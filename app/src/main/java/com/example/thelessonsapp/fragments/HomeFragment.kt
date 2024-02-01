package com.example.thelessonsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thelessonsapp.MainActivity
import com.example.thelessonsapp.R
import com.example.thelessonsapp.adapter.LessonAdapter
import com.example.thelessonsapp.databinding.FragmentHomeBinding
import com.example.thelessonsapp.model.Lesson
import com.example.thelessonsapp.viewmodel.LessonViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {


    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var lessonsViewModel: LessonViewModel
    private lateinit var lessonAdapter: LessonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate three layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        lessonsViewModel = (activity as MainActivity).lessonViewModel
        setupHomeRecyclerView()

        binding.addLessonFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addLessonFragment)

        }
    }

    private fun updateUI(lesson: List<Lesson>?){
        if (lesson != null) {
            if (lesson.isNotEmpty()){
                binding.emptyLessonsImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyLessonsImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView(){
        lessonAdapter = LessonAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = lessonAdapter
        }

        activity?.let{
            lessonsViewModel.getAllLessons().observe(viewLifecycleOwner){ lesson ->
                lessonAdapter.differ.submitList(lesson)
                updateUI(lesson)
            }
        }
    }

    private fun searchLesson(query: String?){
        val searchQuery = "%$query"

        lessonsViewModel.searchLesson(searchQuery).observe(this){list ->
            lessonAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchLesson(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}