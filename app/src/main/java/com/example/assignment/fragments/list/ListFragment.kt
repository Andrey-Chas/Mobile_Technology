package com.example.assignment.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.databinding.FragmentListBinding
import com.example.assignment.viewmodel.UserViewModel

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var userViewModel: UserViewModel
    private val myAdapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding = FragmentListBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.searchView.setOnQueryTextListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        userViewModel.readAllData.observe(viewLifecycleOwner) { user ->
            myAdapter.setData(user)
        }

    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
                }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.delete_menu) {
                    deleteAllUsers()
                }

                return true
            }

            private fun deleteAllUsers() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    userViewModel.deleteAllUsers()
                    Toast.makeText(requireContext(), "Successfully removed everything", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No") { _, _ ->

                }
                builder.setTitle("Delete everything?")
                builder.setMessage("Are sure you want to delete everything?")
                builder.create().show()
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.delete_menu, menu)
//        var menuItem = menu!!.findItem(R.id.menu_search)
//        var searchView: androidx.appcompat.widget.SearchView = menuItem.actionView as androidx.appcompat.widget.SearchView
//
//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                myAdapter.filter.filter(newText)
//                return true
//            }
//
//        })
//        return super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.delete_menu, menu)
//
//        val searchItem = menu.findItem(R.id.menu_search)
//        val searchView = searchItem.actionView as SearchView
//
//        searchView.onQueryTextChanged {
//            mUserViewModel.searchQuery.value = it
//        }
//
//    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.layout.fragment_list, menu)
//
//        val search = menu?.findItem(R.id.menu_search)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//    }
//
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }

        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        userViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                myAdapter.setData(it)
            }
        }
    }
}