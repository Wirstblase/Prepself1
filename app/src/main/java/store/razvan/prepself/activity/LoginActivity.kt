package store.razvan.prepself.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.databinding.ActivityLoginBinding
import store.razvan.prepself.databinding.ActivityLoginBinding.inflate
import store.razvan.prepself.utils.email
import store.razvan.prepself.utils.login
import store.razvan.prepself.utils.password
import kotlin.concurrent.thread


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var resetPasswordButton: Button
    private lateinit var emailAddressInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        loginButton = binding.loginButton
        registerButton = binding.registerButton
        resetPasswordButton = binding.resetPasswordButton
        emailAddressInput = binding.emailAddressInput
        passwordInput = binding.passwordInput


        resetPasswordButton.background.alpha = 0
        resetPasswordButton.stateListAnimator = null
        resetPasswordButton.setTextColor(Color.parseColor("#FFFFFF"))

        registerButton.background.alpha = 0
        registerButton.stateListAnimator = null
        registerButton.setTextColor(Color.parseColor("#FFFFFF"))

        loginButton.setOnClickListener {
            email = emailAddressInput.text.toString()
            password = passwordInput.text.toString()
            val thread = thread(true) {
                login(context = this, email = email, password = password)
                Log.i("DA", "nU")
            }
            thread.join()
        }

        resetPasswordButton.setOnClickListener {
            val intent = Intent(this, PasswordResetActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            email = emailAddressInput.text.toString()
            password = passwordInput.text.toString()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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