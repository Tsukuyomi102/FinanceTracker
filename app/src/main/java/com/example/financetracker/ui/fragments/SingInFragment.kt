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
import com.example.financetracker.data.User
import com.example.financetracker.databinding.FragmentSingInBinding
import com.example.financetracker.network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SingInFragment : Fragment() {
    private lateinit var binding: FragmentSingInBinding
    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingInBinding.inflate(layoutInflater, container,false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE
        binding.textViewForgot.visibility = View.GONE

        binding.textViewReg.setOnClickListener(){
            findNavController().navigate(R.id.action_singInFragment_to_singUpFragment)
        }

        binding.btnLogin.setOnClickListener() {
            if (binding.editLog.text.toString().isEmpty()){
                Toast.makeText(
                    context,
                    "Вы не ввели почту",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                val call = apiService.loginUser(
                    binding.editLog.text.toString(),
                    binding.editPassword.text.toString()
                )

                call.enqueue(object : Callback<User>{

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.isSuccessful){
                            findNavController().navigate(R.id.action_singInFragment_to_dashboardFragment)
                        } else {
                            println("Response not successful: ${response.errorBody()?.string()}")
                            binding.textViewForgot.visibility = View.VISIBLE
                            Toast.makeText(context,
                                "Не удалось войти. " +
                                        "Возможно, вы неправильно ввели почту или пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        println("Error: ${t.message}")
                        Toast.makeText(context,
                            "Ошибка на сервере, пожалуйста, попробуйте авторизоваться позднее",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

        return binding.root
    }

}