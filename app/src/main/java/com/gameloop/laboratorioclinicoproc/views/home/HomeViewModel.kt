package com.gameloop.laboratorioclinicoproc.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _inPatientsFragment = MutableLiveData<Boolean>()
    val inPatientsFragment: LiveData<Boolean> = _inPatientsFragment

    private val _eventNavigateToAddUser = MutableLiveData<Boolean>()
    val eventNavigateToAddUser: LiveData<Boolean> = _eventNavigateToAddUser

    fun onExitPatientsFragment() {
        _inPatientsFragment.value = false
    }

    fun onEnterPatientsFragment() {
        _inPatientsFragment.value = true
    }

    fun navigateToAddUser() {
        _eventNavigateToAddUser.value = true
    }

    fun navigateToAddUserComplete() {
        _eventNavigateToAddUser.value = false
    }
}