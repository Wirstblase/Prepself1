package store.razvan.prepself.utils

import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import org.json.JSONException
import org.json.JSONObject
import store.razvan.prepself.activity.LoginActivity
import store.razvan.prepself.models.RecipeResponse
import store.razvan.prepself.response.SimpleResponse
import store.razvan.prepself.response.TokenResponse
import store.razvan.prepself.response.UserInfoResponse
import java.io.IOException

fun changeEmail(context: AppCompatActivity, newEmail: String) {
    val jsonObject = JSONObject()
    try {
        jsonObject.put("email", newEmail)
        Log.i("new email address", newEmail)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val requestBody = jsonObject.toString().toRequestBody(json)

    val request = Request.Builder()
        .method("POST", requestBody)
        .header("token", token)
        .url(URL + "user-management/modify-email")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Check your inbox!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send email change request")
        }
    }
}

fun checkToken(context: AppCompatActivity): Boolean {
    val request = Request.Builder()
        .get()
        .header("token", token)
        .url(URL + "user-management/check")
        .build()
//    Toast.makeText(context, "blah", Toast.LENGTH_SHORT).show()
    var logged = false
    try {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                context.runOnUiThread {
                    Toast.makeText(context, "Internal Server Error.", Toast.LENGTH_LONG).show()
                }
            } else {
                val body = response.body!!.string()
                Log.e("BODY", body)

                val readValue = mapper.readValue(body, SimpleResponse::class.java)
                if (readValue.success) {
                    logged = true
                } else {
                    clearFile(context)
                    context.runOnUiThread {
                        Toast.makeText(context, readValue.errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        context.runOnUiThread {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
    return logged
}

fun login(context: AppCompatActivity, email: String, password: String) {
    val jsonObject = JSONObject()
    try {
        jsonObject.put("email", email)
        jsonObject.put("password", password)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val requestBody = jsonObject.toString().toRequestBody(json)

    val request = Request.Builder()
        .method("POST", requestBody)
        .url(URL + "login")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            context.runOnUiThread {
                Toast.makeText(context, "Login unsuccessful", Toast.LENGTH_LONG).show()
            }
        } else {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, TokenResponse::class.java)
            token = readValue.tokenString
            saveToken(context)
            openDiscoveryActivity(context)
        }
    }
}

fun registerUser(
    context: AppCompatActivity,
    firstName: String,
    lastName: String,
    email: String,
    password: String
) {

    val jsonObject = JSONObject()
    try {
        jsonObject.put("firstName", firstName)
        jsonObject.put("lastName", lastName)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val requestBody = jsonObject.toString().toRequestBody(json)

    val request = Request.Builder()
        .method("POST", requestBody)
        .url(URL + "registration")
        .build()

    val response = client.newCall(request).execute()
    val result = response.body?.string().toString()

    val jsonObj = JSONObject(result)

    try {
        val message = jsonObj.get("message").toString()
        context.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    } catch (e: Exception) {
        context.runOnUiThread {
            Toast.makeText(context, "Confirmation email has been sent", Toast.LENGTH_LONG).show()
        }
    }
}

fun changePassword(context: AppCompatActivity, newPassword: String) {
    val jsonObject = JSONObject()
    try {
        jsonObject.put("email", email)
        jsonObject.put("firstName", null)
        jsonObject.put("lastName", null)
        jsonObject.put("password", newPassword)
        jsonObject.put("promoteUser", false)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val requestBody = jsonObject.toString().toRequestBody(json)

    val request = Request.Builder()
        .method("PATCH", requestBody)
        .header("token", token)
        .url(URL + "user-management/update")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            Log.i("response", response.body!!.string())
            displayMessage(context, "Failed to update information")
        } else {
            displayMessage(context, "Password has been changed")
        }
    }
}

fun sendPasswordResetRequest(context: AppCompatActivity, email: String) {

    val jsonObject = JSONObject()
    try {
        jsonObject.put("email", email)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val requestBody = jsonObject.toString().toRequestBody(json)

    val request = Request.Builder()
        .method("POST", requestBody)
        .url(URL + "user-management/request-password-reset")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        val body = response.body!!.string()
        val readValue = mapper.readValue(body, SimpleResponse::class.java)
        val errorMessage = readValue.errorMessage

        if (errorMessage == null) {
            context.runOnUiThread {
                Toast.makeText(context, "Password reset email has been sent", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            context.runOnUiThread {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }
}

fun sendLogoutRequest(context: AppCompatActivity) {
    val request = Request.Builder()
        .method("PATCH", EMPTY_REQUEST)
        .header("token", token)
        .url(URL + "user-management/logout")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            displayMessage(context, "You have been logged out")
            openActivity(context, LoginActivity::class.java)
            clearFile(context)
        } else {
            displayMessage(context, "Failed to log you out")
        }
    }
}

fun getUserInfo(context: AppCompatActivity, firstNameInput: EditText, lastNameInput: EditText) {
    val request = Request.Builder()
        .method("GET", null)
        .header("token", token)
        .url(URL + "user-management/get-user-info")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, UserInfoResponse::class.java)
            if (readValue.success) {
                firstName = readValue.firstName.toString()
                lastName = readValue.lastName.toString()
                email = readValue.email.toString()
                firstNameInput.setText(firstName)
                lastNameInput.setText(lastName)
            } else {
                displayMessage(context, "Could not retrieve user account information")
            }
        } else {
            displayMessage(context, "Could not retrieve user account information")
        }
    }
}

fun updateUserInfo(context: AppCompatActivity, firstNameInput: EditText, lastNameInput: EditText) {

    val newFirstName = firstNameInput.text.toString()
    val newLastName = lastNameInput.text.toString()

    val jsonObject = JSONObject()
    try {
        Log.i("email", email)
        jsonObject.put("email", email)
        if (firstName != newFirstName) {
            jsonObject.put("firstName", newFirstName)
        }
        if (lastName != newLastName) {
            jsonObject.put("lastName", newLastName)
        }
        jsonObject.put("promoteUser", false)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    val requestBody = jsonObject.toString().toRequestBody(json)

    val request = Request.Builder()
        .method("PATCH", requestBody)
        .header("token", token)
        .url(URL + "user-management/update")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            Log.i("response", response.body!!.string())
            displayMessage(context, "Failed to update information")
        } else {
            displayMessage(context, "Information updated")
        }
    }
}

fun getPrepareNext(context: AppCompatActivity): RecipeResponse? {
    val request = Request.Builder()
        .get()
        .header("token", token)
        .url(URL + "recipes/preferences/prepare-next")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(RecipeResponse::class.java)
            val recipe: RecipeResponse = jsonAdapter.fromJson(response.body!!.string())!!
            if (recipe.success) {
                return recipe
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", recipe.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
    return null
}

fun getBlacklist(context: AppCompatActivity): RecipeResponse? {
    val request = Request.Builder()
        .get()
        .header("token", token)
        .url(URL + "recipes/preferences/blacklist")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(RecipeResponse::class.java)
            val recipe: RecipeResponse = jsonAdapter.fromJson(response.body!!.string())!!
            if (recipe.success) {
                return recipe
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", recipe.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
    return null
}

fun getFavourites(context: AppCompatActivity): RecipeResponse? {
    val request = Request.Builder()
        .get()
        .header("token", token)
        .url(URL + "recipes/preferences/favourites")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(RecipeResponse::class.java)
            val recipe: RecipeResponse = jsonAdapter.fromJson(response.body!!.string())!!
            if (recipe.success) {
                return recipe
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", recipe.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
    return null
}

fun removeFromPrepareNext(context: AppCompatActivity, id: Long) {
    val request = Request.Builder()
        .delete()
        .header("token", token)
        .url(URL + "recipes/preferences/remove-from-prepare-next?recipeId=$id")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Recipe removed!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
}

fun removeFromBlacklist(context: AppCompatActivity, id: Long) {
    val request = Request.Builder()
        .delete()
        .header("token", token)
        .url(URL + "recipes/preferences/remove-from-blacklist?recipeId=$id")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Recipe removed!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
}

fun removeFromFavourites(context: AppCompatActivity, id: Long) {
    val request = Request.Builder()
        .delete()
        .header("token", token)
        .url(URL + "recipes/preferences/remove-from-favourite?recipeId=$id")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Recipe removed!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
}

fun addRecipeToPrepareNext(context: AppCompatActivity, id: Long) {
    val request = Request.Builder()
        .method("POST", EMPTY_REQUEST)
        .header("token", token)
        .url(URL + "recipes/preferences/add-to-prepare-next?recipeId=$id")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Recipe added!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
}

fun addRecipeToBlacklist(context: AppCompatActivity, id: Long) {
    val request = Request.Builder()
        .method("POST", EMPTY_REQUEST)
        .header("token", token)
        .url(URL + "recipes/preferences/add-to-blacklist?recipeId=$id")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            Log.e("HTML", body)
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Recipe added!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
}

fun addRecipeToFavourites(context: AppCompatActivity, id: Long) {
    val request = Request.Builder()
        .method("POST", EMPTY_REQUEST)
        .header("token", token)
        .url(URL + "recipes/preferences/add-to-favourites?recipeId=$id")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            val body = response.body!!.string()
            val readValue = mapper.readValue(body, SimpleResponse::class.java)
            if (readValue.success) {
                displayMessage(context, "Recipe added!")
            } else {
                displayMessage(context, "Failed to send email")
                Log.i("error", readValue.errorMessage.toString())
            }
        } else {
            displayMessage(context, "Failed to send request")
        }
    }
}