package ru.trinitydigital.firebasebackend.data.api

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.trinitydigital.firebasebackend.data.model.NewsItem


interface FirebaseApi {
    fun loadData(): MutableLiveData<ArrayList<NewsItem>>
}

class FirebaseApiImpl : FirebaseApi {

    override fun loadData(): MutableLiveData<ArrayList<NewsItem>> {
        val data = MutableLiveData<ArrayList<NewsItem>>()

        val database = Firebase.database
        val ref = database.getReferenceFromUrl("https://first-backend-firebase.firebaseio.com/")

        val list: ArrayList<NewsItem>? = arrayListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val point = postSnapshot.getValue(NewsItem::class.java)
                    point?.let { list?.add(it) }
                }

                data.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                data.postValue(null)
            }
        })

        return data
    }
}