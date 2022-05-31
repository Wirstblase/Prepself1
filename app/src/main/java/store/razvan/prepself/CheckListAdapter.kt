package store.razvan.prepself

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_checklist.view.*
import kotlinx.android.synthetic.main.item_view_checklist.view.*
import kotlinx.android.synthetic.main.item_view_recipelist.view.*
import store.razvan.prepself.databinding.ActivityChecklistBinding
import store.razvan.prepself.models.Recipe
import store.razvan.prepself.utils.getThumbnail

class CheckListAdapter (private var recipes: List<Recipe>, val binder: ActivityChecklistBinding, val context: AppCompatActivity
): RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {

    inner class CheckListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListAdapter.CheckListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_checklist, parent, false)
        //view.setOnTouchListener(OnSwipeTouchListener(context))
        return CheckListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckListAdapter.CheckListViewHolder, position: Int) {
        val curImage = getThumbnail(recipe = recipes[position])


        val itemColor: Int = Color.parseColor("#363636")
        val easyColor: Int = Color.parseColor("#63bf21")
        val normalColor: Int = Color.parseColor("#f0d85d")
        val hardColor: Int = Color.parseColor("#fc7005")
        val expertColor: Int = Color.parseColor("#fc1a05")

        /*holder.itemView.recipelistitem_separator.background = roundedCornersDrawable(0,
            Color.parseColor("#FFFFFF"), cornerRadius = 5f,
            Color.parseColor("#FFFFFF"))
        holder.itemView.recipelistitem_view.background = roundedCornersDrawable(5,itemColor, cornerRadius = 25f, itemColor)
        holder.itemView.recipelistitem_text.text = recipes[position].name.toString()
        holder.itemView.recipelistitem_image.setImageBitmap(curImage)
        holder.itemView.recipelistitem_description.text = recipes[position].description.toString()*/

        //holder.itemView.ivImage.setImageBitmap(curImage)
        //holder.itemView.recipeId.text = "$position"

        holder.itemView.checklist_checkbox.text = recipes[position].name.toString()

    }

    override fun getItemCount(): Int {
        Log.e("checklist things number",recipes.size.toString())
        return recipes.size
    }

    // extension function to get rounded corners border
    fun roundedCornersDrawable(
        borderWidth: Int = 10, // border width in pixels
        borderColor: Int = Color.BLACK, // border color
        cornerRadius: Float = 25F, // corner radius in pixels
        bgColor: Int = Color.TRANSPARENT // view background color
    ): Drawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setStroke(borderWidth, borderColor)
            setColor(bgColor)
            // make it rounded corners
            this.cornerRadius = cornerRadius
        }
    }

}

