package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.api.ApiService
import com.example.financetracker.databinding.FragmentSingUpBinding
import com.example.financetracker.network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingUpFragment : Fragment() {
    private lateinit var binding: FragmentSingUpBinding
    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSingUpBinding.inflate(layoutInflater, container, false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE
        binding.textPasswordLength.visibility = View.GONE

        binding.signIn.setOnClickListener(){
            findNavController().navigate(R.id.action_singUpFragment_to_singInFragment)
        }

        binding.buttonDisclose.setOnClickListener(){
            if (binding.editPassword.inputType == 129) {
                binding.buttonDisclose.setImageResource(R.drawable.eye)
                binding.editPassword.inputType = 1
            } else {
                binding.buttonDisclose.setImageResource(R.drawable.icon_solid_eye_off)
                binding.editPassword.inputType = 129
            }
        }

        binding.buttonSignUp.setOnClickListener(){
            if (binding.editName.text.toString().isEmpty()){
                Toast.makeText(
                    context,
                    "Вы не ввели имя",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!binding.editEmail.text.toString().contains("@")) {
                Toast.makeText(
                    context,
                    "Введите, пожалуйста существующую почту",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.editPassword.text.toString().length < 8){
                binding.textPasswordLength.visibility = View.VISIBLE
            } else{
                binding.textPasswordLength.visibility = View.GONE
                val call = apiService.addUser(
                    binding.editName.text.toString(),
                    binding.editEmail.text.toString(),
                    binding.editPassword.text.toString()
                )

                call.enqueue(object : Callback<Integer> {
                    override fun onResponse(call: Call<Integer>, response: Response<Integer>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Пользователь создан",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            println("Response not successful: ${response.errorBody()?.string()}")
                            Toast.makeText(
                                context,
                                "Пользователь не создан, вы неправильно ввели почту",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Integer>, t: Throwable) {
                        println("Error: ${t.message}")
                        Toast.makeText(context,
                            "Ошибка на сервере, пожалуйста, попробуйте зарегистрироваться позднее",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })}
        }
        return binding.root
    }
}