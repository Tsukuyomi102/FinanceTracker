package com.example.financetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financetracker.data.User

class UserViewModel : ViewModel() {
    private var _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    fun addUser(user: User) {
        _userLiveData.postValue(user)
    }
}
