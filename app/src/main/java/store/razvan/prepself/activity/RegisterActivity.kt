package store.razvan.prepself.activity

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.databinding.ActivityRegisterBinding
import store.razvan.prepself.databinding.ActivityRegisterBinding.inflate
import store.razvan.prepself.utils.email
import store.razvan.prepself.utils.registerUser
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        emailInput = binding.emailAddressInput
        passwordInput = binding.passwordInput
        firstNameInput = binding.firstNameInput
        lastNameInput = binding.lastNameInput
        registerButton = binding.registerButton
        emailInput.setText(email)
//        passwordInput.setText(password)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val thread = thread  {
                registerUser(this, firstName, lastName, email, password)
            }

            thread.start()
            thread.join()
        }

        setContentView(binding.root)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focus: View? = currentFocus
        if (focus != null) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(focus.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}