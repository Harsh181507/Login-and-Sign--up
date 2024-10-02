package com.example.loginandsignupwithfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginandsignupwithfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.registerbutton.setOnClickListener {
            val userName = binding.userName.text.toString()
            val password = binding.password.text.toString()

            if(userName.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"Please Fill all the details",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(userName,password)
                    .addOnCompleteListener{ task->
                        if(task.isSuccessful){
                            startActivity(Intent(this,MainActivity::class.java))
                            Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this,"Sign-in Failed: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
        binding.loginbutton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

    }
}