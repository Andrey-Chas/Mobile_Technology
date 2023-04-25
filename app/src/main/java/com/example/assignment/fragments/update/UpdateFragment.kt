package com.example.assignment.fragments.update

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment.R
import com.example.assignment.model.User
import com.example.assignment.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupMenu()

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<TextInputEditText>(R.id.updateFirstName).setText(args.currentUser.firstName)
        view.findViewById<TextInputEditText>(R.id.updateLastName).setText(args.currentUser.lastName)
        view.findViewById<TextInputEditText>(R.id.updatePhoneNumber).setText(args.currentUser.telNum)

        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        val firstName = requireView().findViewById<EditText>(R.id.updateFirstName).text.toString()
        val lastName = requireActivity().findViewById<EditText>(R.id.updateLastName).text.toString()
        val phoneNumber = requireView().findViewById<EditText>(R.id.updatePhoneNumber).text.toString()

        if(inputCheck(firstName, lastName, requireView().findViewById<EditText>(R.id.updatePhoneNumber).text)) {
            val updateUser = User(args.currentUser.id, firstName, lastName, phoneNumber)
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.delete_menu) {
                    deleteUser()
                }

                return true
            }

            private fun deleteUser() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    mUserViewModel.deleteUser(args.currentUser)
                    Toast.makeText(requireContext(), "Successfully removed: ${args.currentUser.firstName}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                }
                builder.setNegativeButton("No") { _, _ ->

                }
                builder.setTitle("Delete ${args.currentUser.firstName}?")
                builder.setMessage("Are sure you want to delete ${args.currentUser.firstName}?")
                builder.create().show()
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun inputCheck(firstName: String, lastName: String, phoneNumber: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || phoneNumber.isEmpty())
    }
}