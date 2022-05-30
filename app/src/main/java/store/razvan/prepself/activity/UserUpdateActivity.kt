package store.razvan.prepself.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.R
import store.razvan.prepself.databinding.ActivityUserUpdateBinding
import store.razvan.prepself.databinding.ActivityUserUpdateBinding.inflate
import store.razvan.prepself.utils.*
import kotlin.concurrent.thread


class UserUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserUpdateBinding
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var updateButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var chageEmailButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        firstNameInput = binding.firstNameAccountUpdateInput
        lastNameInput = binding.lastNameAccountUpdateInput
        updateButton = binding.updateButton
        changePasswordButton = binding.changePasswordButton
        chageEmailButton = binding.changeEmailButton
        logoutButton = binding.logoutButton

        val button = binding.menu.userInfoButton
        button.layoutParams.width = 60.dpToPixels(this)
        button.layoutParams.height = 60.dpToPixels(this)
        button.background = getDrawable(R.drawable.selected_menu)

        changePasswordButton.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        chageEmailButton.setOnClickListener {
            val intent = Intent(this, ChangeEmailActivity::class.java)
            startActivity(intent)
        }

        updateButton.setOnClickListener {
            val thread = thread(start = true) {
                updateUserInfo(this, firstNameInput, lastNameInput)
            }
            thread.join()
        }

        logoutButton.setOnClickListener {
            val thread = thread(start = true) {
                sendLogoutRequest(this)
            }
            thread.join()
        }

        val thread = thread(start = true) {
            getUserInfo(this, firstNameInput, lastNameInput)
        }
        thread.join()

        setContentView(binding.root)
        clickListeners()
    }

    private fun clickListeners() {
        binding.menu.fridgeButton.setOnClickListener { openFridgeActivity(this) }
        binding.menu.cookingListButton.setOnClickListener { openRecipeListActivity(this) }
        binding.menu.discoveryButton.setOnClickListener { openDiscoveryActivity(this) }
        binding.menu.checklistButton.setOnClickListener { openChecklistActivity(this) }
    }
}