package store.razvan.prepself

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view_pager.view.*
import store.razvan.prepself.databinding.ActivityMainScreenBinding
import store.razvan.prepself.models.Recipe
import store.razvan.prepself.utils.OnSwipeTouchListener
import store.razvan.prepself.utils.getThumbnail

class ViewPagerAdapter(
    private var recipes: List<Recipe>,
    val binder: ActivityMainScreenBinding,
    val context: AppCompatActivity
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    var nextId = 0
    var lastKnownId = -1

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        view.setOnTouchListener(OnSwipeTouchListener(context))
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val curImage = getThumbnail(recipe = recipes[position])

        if (position == 0 && lastKnownId == -1) {
            val recipe = recipes[0]
            binder.name.text = recipe.name
            binder.preptime.text = String.format(
                "Preptime: %s | Servings: %s | Dificulty: %s",
                recipe.prepTime,
                recipe.servings,
                recipe.difficulty
            )
        }

        holder.itemView.ivImage.setImageBitmap(curImage)
        holder.itemView.recipeId.text = "$position"
    }

    override fun onViewAttachedToWindow(holder: ViewPagerViewHolder) {
        nextId = holder.adapterPosition
    }

    override fun onViewDetachedFromWindow(holder: ViewPagerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.adapterPosition != nextId) {
            val recipe = recipes[nextId]
            binder.name.text = recipe.name
            binder.preptime.text = String.format(
                "Preptime: %s | Servings: %s | Dificulty: %s",
                recipe.prepTime,
                recipe.servings,
                recipe.difficulty
            )
        }
        lastKnownId = holder.adapterPosition
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}