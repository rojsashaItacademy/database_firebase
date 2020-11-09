package ru.trinitydigital.firebasebackend.ui.main

import androidx.lifecycle.ViewModel
import ru.trinitydigital.firebasebackend.data.repository.FirebaseRepositoryImpl

class MainViewModel : ViewModel() {

    private val repository = FirebaseRepositoryImpl()

    fun showItems() = repository.loadData()

}