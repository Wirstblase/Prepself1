package store.razvan.prepself.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.databinding.ActivityChangePasswordBinding
import store.razvan.prepself.databinding.ActivityChangePasswordBinding.inflate
import store.razvan.prepself.utils.changePassword
import store.razvan.prepself.utils.displayMessage
import kotlin.concurrent.thread

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var enterNewPasswordInput: EditText
    private lateinit var confirmNewPassword: EditText
    private lateinit var changePasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        enterNewPasswordInput = binding.enterNewPasswordInput
        confirmNewPassword = binding.confirmNewPasswordInput
        changePasswordButton = binding.changePasswordButton
        setContentView(binding.root)

        changePasswordButton.setOnClickListener {
            thread(start = true) {
                if (enterNewPasswordInput.text.toString() != confirmNewPassword.text.toString()) {
                    displayMessage(this, "Password do not match!")
                } else {
                    changePassword(this, enterNewPasswordInput.text.toString())
                }
            }
        }
    }
}