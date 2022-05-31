package store.razvan.prepself.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import store.razvan.prepself.activity.*
import store.razvan.prepself.models.Recipe
import store.razvan.prepself.models.RecipeResponse

var client = OkHttpClient()
var json = "application/json; charset=utf-8".toMediaTypeOrNull()
var URL = "https://api.prepself.razvan.store/api/v1/"
val mapper = ObjectMapper().registerModule(JavaTimeModule())

fun openActivity(context: AppCompatActivity, clz: Class<*>) {
    context.startActivity(Intent(context, clz))
    context.finish()
}

fun openRecipeListActivity(context: AppCompatActivity) {
    context.startActivity(Intent(context, RecipeListActivity::class.java))
    context.finish();
}

fun openUserProfileActivity(context: AppCompatActivity) {
    context.startActivity(Intent(context, UserUpdateActivity::class.java))
    context.finish();
}

fun openFridgeActivity(context: AppCompatActivity) {
    context.startActivity(Intent(context, FridgeActivity::class.java))
    context.finish();
}

fun openChecklistActivity(context: AppCompatActivity) {
    context.startActivity(Intent(context, CheckListActivity::class.java))
    context.finish();
}

fun openDiscoveryActivity(context: AppCompatActivity) {
    context.startActivity(Intent(context, MainScreenActivity::class.java))
    context.finish();
}

fun getRecipes(): RecipeResponse? {
    val url = URL + "recipes/list"
    val request = Request.Builder().url(url).addHeader("token", token).get().build()

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    var recipe: RecipeResponse? = null
    try {
        val thread = Thread {
            val response = client.newCall(request).execute()
            val jsonAdapter = moshi.adapter(RecipeResponse::class.java)
            recipe = jsonAdapter.fromJson(response.body!!.string())
        }

        thread.start()
        thread.join()
    } catch (e: Exception) {
        println("there was an error with the api call!!!!")
        e.printStackTrace()
    }

    return recipe
}

public fun getThumbnail(recipe: Recipe): Bitmap? {
    val url = URL +
            "recipes/images/thumbnail/?recipeId=${recipe.id}"
    val request = Request.Builder().url(url).addHeader("token", token).get().build()
    var bitMap: Bitmap? = null
    try {
        val thread = Thread {
            var response = client.newCall(request).execute()
            bitMap = BitmapFactory.decodeStream(response.body?.byteStream())


            println(response.body?.string())
        }

        thread.start()
        thread.join()

    } catch (e: Exception) {
        println("there was an error with the api call!!!!")
        e.printStackTrace()
    }
    return bitMap
}

fun displayMessage(context: AppCompatActivity, text: String) {
    context.runOnUiThread {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}

fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()