package store.razvan.prepself.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.R
import store.razvan.prepself.databinding.ActivityFridgeBinding
import store.razvan.prepself.utils.*

class FridgeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFridgeBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)
        binding = ActivityFridgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.menu.fridgeButton
        button.layoutParams.width = 60.dpToPixels(this)
        button.layoutParams.height = 60.dpToPixels(this)
        button.background = getDrawable(R.drawable.selected_menu)

        clickListeners()
    }

    private fun clickListeners() {
        binding.menu.checklistButton.setOnClickListener { openChecklistActivity(this) }
        binding.menu.cookingListButton.setOnClickListener { openRecipeListActivity(this) }
        binding.menu.discoveryButton.setOnClickListener { openDiscoveryActivity(this) }
        binding.menu.userInfoButton.setOnClickListener { openUserProfileActivity(this) }
    }

}