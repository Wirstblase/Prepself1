package store.razvan.prepself.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.*

var token: String = "" // bc69f75f-2328-4af0-8147-79a055d58e2b

var email: String = ""
var password: String = ""
var firstName: String = ""
var lastName: String = ""

fun saveToken(context: AppCompatActivity) {
    writeToFile(context)
}

fun load(context: AppCompatActivity) {
    readFromFile(context)
}


private fun readFromFile(context: Context) {
    try {
        val inputStream: InputStream? = context.openFileInput("token.txt")
        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var receiveString: String? = ""
            val stringBuilder = StringBuilder()
            while (bufferedReader.readLine().also { receiveString = it } != null) {
                stringBuilder.append("\n").append(receiveString)
            }
            inputStream.close()
            token = stringBuilder.toString().trim()
        }
    } catch (e: FileNotFoundException) {
        Log.e("login activity", "File not found: $e")
    } catch (e: IOException) {
        Log.e("login activity", "Can not read file: $e")
    }
}

fun clearFile(context: AppCompatActivity) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(context.openFileOutput("token.txt", Context.MODE_PRIVATE))
        outputStreamWriter.write("", 0, 0)
        outputStreamWriter.close()
    } catch (e: IOException) {
        Log.e("Exception", "File write failed: $e")
    }
}

fun writeToFile(context: AppCompatActivity) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(context.openFileOutput("token.txt", Context.MODE_PRIVATE))
        outputStreamWriter.write(token)
        outputStreamWriter.close()
    } catch (e: IOException) {
        Log.e("Exception", "File write failed: $e")
    }
}