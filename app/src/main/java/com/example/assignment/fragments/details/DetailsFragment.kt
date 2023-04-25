package com.example.assignment.fragments.details

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.assignment.R
import com.example.assignment.databinding.FragmentDetailsBinding
import com.example.assignment.viewmodel.UserViewModel
import org.w3c.dom.Text

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var textView: TextView
    private lateinit var btnCall: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        textView = view.findViewById(R.id.phoneNumber_view)
        btnCall = view.findViewById(R.id.btnCall)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<TextView>(R.id.firstName_view).text = (args.currentUser.firstName)
        view.findViewById<TextView>(R.id.lastName_view).text = (args.currentUser.lastName)
        view.findViewById<TextView>(R.id.phoneNumber_view).text = (args.currentUser.telNum)

        btnCall.setOnClickListener {
            val phoneNumber = textView.text.toString()

            if (phoneNumber.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
            }
        }

        return view
    }
}