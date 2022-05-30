package store.razvan.prepself.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.databinding.ActivityChangeEmailBinding
import store.razvan.prepself.databinding.ActivityChangeEmailBinding.inflate
import store.razvan.prepself.utils.changeEmail
import store.razvan.prepself.utils.email
import kotlin.concurrent.thread

class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeEmailBinding
    private lateinit var changeEmailButton: Button
    private lateinit var newEmailAddressInput: EditText
    private lateinit var currentEmailText: TextView
    private lateinit var currentEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        changeEmailButton = binding.changeEmailButton
        newEmailAddressInput = binding.emailAddressInput
        currentEmailText = binding.currentEmailText
        currentEmail = binding.currnetAccountEmail
        currentEmail.text = email
        setContentView(binding.root)
        changeEmailButton.setOnClickListener {
            thread(start = true) {
                changeEmail(this, newEmailAddressInput.text.toString())
            }
        }
    }
}