package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.focusAndShowKeyboard


class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            edit.focusAndShowKeyboard()
            if (intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                edit.setText(text)
                edit.setSelection(edit.text.length)
            }
            ok.setOnClickListener {
                val newIntent = Intent()
                if (edit.text.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, newIntent)
                } else {
                    val content = edit.text.toString()
                    newIntent.putExtra(Intent.EXTRA_TEXT, content)
                    setResult(Activity.RESULT_OK, newIntent)
                }
                finish()
            }
        }

    }

    object PostResultContract : ActivityResultContract<String, String?>() {
        override fun createIntent(context: Context, input: String): Intent =
            Intent(context, NewPostActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {
                null
            }
    }
}