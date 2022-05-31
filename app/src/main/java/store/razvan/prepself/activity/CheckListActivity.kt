package store.razvan.prepself.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import store.razvan.prepself.CheckListAdapter
import store.razvan.prepself.R
import store.razvan.prepself.RecipeListAdapter
import store.razvan.prepself.databinding.ActivityChecklistBinding
import store.razvan.prepself.models.Recipe
import store.razvan.prepself.utils.*
import kotlin.concurrent.thread

class CheckListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChecklistBinding
    lateinit var checklistRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)
        binding = ActivityChecklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checklistRecyclerView = findViewById(R.id.checklistRecyclerView)

        val button = binding.menu.checklistButton
        button.layoutParams.width = 60.dpToPixels(this)
        button.layoutParams.height = 60.dpToPixels(this)
        button.background = getDrawable(R.drawable.selected_menu)

        clickListeners()


        var recipess: List<Recipe> = ArrayList<Recipe>()
        val context = this@CheckListActivity

        thread(start=true){
            recipess = getPrepareNext(context)!!.body
        }.join()

        val adapter = CheckListAdapter(recipess, binding, context)
        val manager = LinearLayoutManager(this)
        checklistRecyclerView.setLayoutManager(manager);
        checklistRecyclerView.adapter = adapter
    }


    private fun clickListeners() {
        binding.menu.fridgeButton.setOnClickListener { openFridgeActivity(this) }
        binding.menu.cookingListButton.setOnClickListener { openRecipeListActivity(this) }
        binding.menu.discoveryButton.setOnClickListener { openDiscoveryActivity(this) }
        binding.menu.userInfoButton.setOnClickListener { openUserProfileActivity(this) }
    }
}
