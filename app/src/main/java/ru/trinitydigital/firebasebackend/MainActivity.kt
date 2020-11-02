package ru.trinitydigital.firebasebackend

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.trinitydigital.firebasebackend.data.model.NewsItem


class MainActivity : AppCompatActivity() {

    val data = arrayListOf<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database
        val ref = database.getReferenceFromUrl("https://first-backend-firebase.firebaseio.com/")


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for (postSnapshot in snapshot.children) {
                    val point = postSnapshot.getValue(NewsItem::class.java)
                    point?.let { data.add(it) }
                }

                findViewById<TextView>(R.id.tvCount).text = data.size.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("qdadasdasdasd", "2")
            }

        })

    }
}