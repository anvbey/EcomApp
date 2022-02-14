package com.example.ecommerceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import com.example.ecommerceapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text


class RegisterActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    val textName: EditText = findViewById(R.id.enterName)
    val email: EditText = findViewById(R.id.editTextTextPersonName7)
    val password: EditText = findViewById(R.id.editTextTextPersonName5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val login: TextView = findViewById(R.id.textView6)
        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        val back: ImageButton = findViewById(R.id.imageButton)
        back.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val btnRegister: Button = findViewById(R.id.button2)
        btnRegister.setOnClickListener {
            registerUser()
            Toast.makeText(this, "Hi", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateRegisterDetails(): Boolean{

        return when{
            textName.text.toString().isEmpty() ->{
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_first_name), true)
                false
            }
            else -> {
                showErrorSnackBar(resources.getString(R.string.registerSuccess), false)
                true
            }
        }
    }

    private fun registerUser(){

        // Check with validate function if the entries are valid or not.
        if(validateRegisterDetails())
        {
            val emailText: String =  email.text.toString().trim(){
                it <= ' '
            }
            val passwordText: String = password.text.toString().trim{
                it <= ' '
            }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> {
                        task ->
                        // If the registration is successfully done
                        if(task.isSuccessful){
                            //Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            showErrorSnackBar(
                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
                                false
                            )
                        }
                        else{
                            // If the registering is not successful then show error message.
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }

                )
        }
    }

}