package com.example.financetracker.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.data.User
import com.example.financetracker.databinding.FragmentProfileBinding
import com.example.financetracker.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        binding.textCont.setOnClickListener(){
            findNavController().navigate(R.id.action_profileFragment_to_billFragment)
        }

        binding.textHelp.setOnClickListener(){
            findNavController().navigate(R.id.action_profileFragment_to_helpFragment)
        }

        binding.textSettings.setOnClickListener(){
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }


        displayLoggedUser()
        userViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.email.text = user.email
                binding.nickname.text = user.name
            }
        }
        return binding.root
    }

    private fun displayLoggedUser() {
        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        val password = sharedPreferences?.getString("password", "")

        val user = User(
            name = name.toString(),
            email = email.toString(),
            password = password.toString()
        )
        userViewModel.addUser(user)
    }
}