package store.razvan.prepself.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.databinding.ActivityPasswordResetBinding
import store.razvan.prepself.databinding.ActivityPasswordResetBinding.inflate
import store.razvan.prepself.utils.sendPasswordResetRequest
import kotlin.concurrent.thread

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordResetBinding
    private lateinit var submitButton: Button
    private lateinit var emailInputField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        submitButton = binding.submitButton
        emailInputField = binding.resetPasswordEmailAddressInput

        submitButton.setOnClickListener {
            val email = emailInputField.text
            thread(start = true) {
                sendPasswordResetRequest(context = this, email = email.toString())
            }
        }

        setContentView(binding.root)
    }
}