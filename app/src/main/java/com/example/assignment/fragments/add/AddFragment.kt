package com.example.assignment.fragments.add

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding;
import com.example.assignment.R
import com.example.assignment.model.User
import com.example.assignment.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<Button>(R.id.btnAddUser).setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = requireView().findViewById<TextInputEditText>(R.id.addFirstName).text.toString()
        val lastName = requireView().findViewById<TextInputEditText>(R.id.addLastName).text.toString()
        val phoneNumber = requireView().findViewById<TextInputEditText>(R.id.addPhoneNumber).text.toString()

        if (inputCheck(firstName, lastName, phoneNumber)) {
            val user = User(0, firstName, lastName, phoneNumber)
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, phoneNumber: String): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || phoneNumber.isEmpty())
    }
}