package ru.trinitydigital.firebasebackend.ui.main

import androidx.lifecycle.ViewModel
import org.koin.java.KoinJavaComponent.inject
import ru.trinitydigital.firebasebackend.data.repository.FirebaseRepository
import ru.trinitydigital.firebasebackend.data.repository.FirebaseRepositoryImpl

class MainViewModel(private val repository: FirebaseRepository) : ViewModel() {

    fun showItems() = repository.loadData()

}