package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.api.ApiService
import com.example.financetracker.data.User
import com.example.financetracker.databinding.FragmentSingInBinding
import com.example.financetracker.network.RetrofitClient
import com.example.financetracker.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingInFragment : Fragment() {
    private lateinit var binding: FragmentSingInBinding
    private lateinit var userViewModel: UserViewModel

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        val password = sharedPreferences?.getString("password", "")
        if (!name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            findNavController().navigate(R.id.action_singInFragment_to_dashboardFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingInBinding.inflate(layoutInflater, container,false)
        binding.textViewForgot.visibility = View.GONE

        binding.textViewReg.setOnClickListener(){
            findNavController().navigate(R.id.action_singInFragment_to_singUpFragment)
        }

        binding.btnLogin.setOnClickListener() {
            userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
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
                            val user = User(
                                email = response.body()?.email.toString(),
                                password = response.body()?.password.toString(),
                                name = response.body()?.name.toString()
                            )

                            saveUserData(
                                response.body()?.name.toString(),
                                response.body()?.password.toString(),
                                response.body()?.name.toString()
                            )
                            userViewModel.addUser(user)
                            saveLoggedUser(user)
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

    private fun saveUserData(name: String, email: String, password: String) {
        val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putString("name", name)
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    private fun saveLoggedUser(user: User) {
        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putString("name", user.name)
            putString("email", user.email)
            putString("password", user.password)
            apply()
        }
    }
}