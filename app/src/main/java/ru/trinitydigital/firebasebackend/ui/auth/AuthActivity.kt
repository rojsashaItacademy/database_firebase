package ru.trinitydigital.firebasebackend.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.*
import ru.trinitydigital.firebasebackend.R
import ru.trinitydigital.firebasebackend.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AuthActivity : AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel>()


    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = Firebase.auth
        setupCallback()
        setupListeners()
    }

    private fun startTimer() {
        btnSentPhone.isEnabled = false

        val timer = object : CountDownTimer(60_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
//
//                dateFormat(millisUntilFinished)
                val seconds = (millisUntilFinished / 1000).toInt()
                tvTimer.text = dateFormat(millisUntilFinished)
                tvTimer.visibility = View.VISIBLE
            }

            override fun onFinish() {
                btnSentPhone.isEnabled = true
                tvTimer.visibility = View.GONE
            }
        }
        timer.start()
    }

    fun dateFormat(millisUntilFinished: Long): String {
        val format = SimpleDateFormat("mm:ss", Locale.getDefault())
        val date = Date(millisUntilFinished)

        return format.format(date)

    }

    private fun setupListeners() {
        btnSentPhone.setOnClickListener {
            verifyPhone(etInputNumber.text.toString())
            startTimer()
        }

        btnSentCode.setOnClickListener {
            storedVerificationId?.let { verificationId ->
                val credential = PhoneAuthProvider.getCredential(
                    verificationId,
                    etInputCode.text.toString()
                )

                signInWithPhoneAuthCredential(credential)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("adasdasdasd", "signInWithCredential:success")

                    val user = task.result?.user

                    user?.getIdToken(true)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            val idToken: String? = it.result?.token
                            startActivity(Intent(this, MainActivity::class.java))
                            // ...
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("adasdasdasd", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

    private fun setupCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(applicationContext, "onVerificationFailed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, p1)
                storedVerificationId = verificationId
            }
        }
    }

    private fun verifyPhone(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]

    }
}