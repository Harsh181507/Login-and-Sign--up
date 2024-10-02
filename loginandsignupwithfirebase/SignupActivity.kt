package com.example.loginandsignupwithfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupwithfirebase.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private val binding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.loginbutton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.registerbutton.setOnClickListener {
            // Getting text from User to Create Account
            val email = binding.email.text.toString().trim()
            val userName = binding.userName.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val reEnterPassword = binding.reEnter.text.toString().trim()

            if (email.isEmpty() || userName.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_LONG).show()
            } else if (password != reEnterPassword) {
                Toast.makeText(this, "Repeated password must be the same", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Account Creation Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }




    }
}