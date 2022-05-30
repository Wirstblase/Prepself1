package store.razvan.prepself.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import store.razvan.prepself.R
import store.razvan.prepself.RecipeListAdapter
import store.razvan.prepself.databinding.ActivityRecipeListBinding
import store.razvan.prepself.models.Recipe
import store.razvan.prepself.utils.*
import kotlin.concurrent.thread


class RecipeListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRecipeListBinding
    lateinit var recipeListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeListRecyclerView = findViewById(R.id.recipeListRecyclerView)

        val button = binding.menu.cookingListButton
        button.layoutParams.width = 60.dpToPixels(this)
        button.layoutParams.height = 60.dpToPixels(this)
        button.background = getDrawable(R.drawable.selected_menu)

        clickListeners()

        var context = this@RecipeListActivity

        Log.e("token", token)

        var recipess: List<Recipe> = ArrayList<Recipe>()

        thread(start=true){
            recipess = getPrepareNext(context)!!.body
        }.join()

        /*if(recipess.isEmpty()){
            Log.e("recipess isEmpty true","true")
        } else {

        }*/

        val adapter = RecipeListAdapter(recipess, binding, context)
        println(adapter)
        println(adapter.nextId)
        println(adapter.itemCount)
        val manager = LinearLayoutManager(this)
        recipeListRecyclerView.setLayoutManager(manager);
        recipeListRecyclerView.adapter = adapter //recipeListRecyclerView.orientation = ViewPager2.ORIENTATION_VERTICAL

    }

    private fun clickListeners() {
        binding.menu.fridgeButton.setOnClickListener{ openFridgeActivity(this) }
        binding.menu.discoveryButton.setOnClickListener { openDiscoveryActivity(this) }
        binding.menu.checklistButton.setOnClickListener { openChecklistActivity(this) }
        binding.menu.userInfoButton.setOnClickListener { openUserProfileActivity(this) }
    }
}