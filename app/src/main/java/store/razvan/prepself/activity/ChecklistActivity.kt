package store.razvan.prepself.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import store.razvan.prepself.R
import store.razvan.prepself.databinding.ActivityChecklistBinding
import store.razvan.prepself.utils.*

class ChecklistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChecklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)
        binding = ActivityChecklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.menu.checklistButton
        button.layoutParams.width = 60.dpToPixels(this)
        button.layoutParams.height = 60.dpToPixels(this)
        button.background = getDrawable(R.drawable.selected_menu)

        clickListeners()
    }


    private fun clickListeners() {
        binding.menu.fridgeButton.setOnClickListener { openFridgeActivity(this) }
        binding.menu.cookingListButton.setOnClickListener { openRecipeListActivity(this) }
        binding.menu.discoveryButton.setOnClickListener { openDiscoveryActivity(this) }
        binding.menu.userInfoButton.setOnClickListener { openUserProfileActivity(this) }
    }
}
