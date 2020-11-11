package ru.trinitydigital.firebasebackend.data.repository

import androidx.lifecycle.MutableLiveData
import ru.trinitydigital.firebasebackend.data.api.FirebaseApi
import ru.trinitydigital.firebasebackend.data.api.FirebaseApiImpl
import ru.trinitydigital.firebasebackend.data.model.NewsItem

interface FirebaseRepository {
    fun loadData(): MutableLiveData<ArrayList<NewsItem>>
}

class FirebaseRepositoryImpl(private val network: FirebaseApi) : FirebaseRepository {

    override fun loadData(): MutableLiveData<ArrayList<NewsItem>> {
        val data = network.loadData()

        /*
        save in room, sharedPreference
        save in room, sharedPreference
        save in room, sharedPreference
        save in room, sharedPreference
        save in room, sharedPreference
       */

        return data
    }
}