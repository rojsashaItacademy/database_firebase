package ru.trinitydigital.firebasebackend.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.trinitydigital.firebasebackend.R
import ru.trinitydigital.firebasebackend.ui.auth.AuthActivity
import ru.trinitydigital.firebasebackend.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val mUser = FirebaseAuth.getInstance().currentUser

        if (mUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
        } else {
            loadToken(mUser)
        }
    }

    private fun loadToken(mUser: FirebaseUser) {
        mUser.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    // ...
                } else {
                    startActivity(Intent(this, AuthActivity::class.java))
                }
            }
    }
}