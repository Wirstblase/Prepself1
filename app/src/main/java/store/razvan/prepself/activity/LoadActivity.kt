package store.razvan.prepself.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.wasabeef.blurry.Blurry
import store.razvan.prepself.R
import store.razvan.prepself.utils.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.thread


class LoadActivity : AppCompatActivity() {

    lateinit var loadBgImageView: ImageView
    lateinit var loadLogoImageView: ImageView
    lateinit var extraView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        loadBgImageView = findViewById(R.id.loadBgImageView)
        loadLogoImageView = findViewById(R.id.loadLogoImageView)
        extraView = findViewById(R.id.loadExtraView)

        val view: View = this.findViewById(android.R.id.content)
        val context = this@LoadActivity
        load(context)

        val hasTokenInCache = token.isNotEmpty()
        var logged = false

        if (hasTokenInCache) {
            val thread = thread(start = true) {
                logged = checkToken(this)
            }
            thread.join()
        }

        loadLogoImageView.post {
            Blurry.with(context)
                .radius(15)
                .sampling(5)
                .capture(view)
                .into(loadBgImageView)
            Timer().schedule(1000) {
                if (logged) {
                    openDiscoveryActivity(context)
                } else {
                    openActivity(context, LoginActivity::class.java)
                }
            }
        }
    }
}