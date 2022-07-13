package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.FeedFragment.Companion.textArg
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val unboxIntentText = intent.getStringExtra(Intent.EXTRA_TEXT)

            if (unboxIntentText.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    R.string.error_empty_content,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    finish()
                }.show()
            } else {

               //only for activity
                val fragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

                //send text in newPostFragment with text
                fragment.navController.navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = unboxIntentText })
            }
        }
    }
}