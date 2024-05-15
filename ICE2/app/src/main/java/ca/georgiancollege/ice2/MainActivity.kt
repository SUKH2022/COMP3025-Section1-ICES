package ca.georgiancollege.ice2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgiancollege.ice2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

//        instantiates an object of type ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()

//sets the content view to the "super view" or main view grp
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        this line created the reference to the TxtView
//        val helloWorldTextView = findViewById<TextView>(R.id.helloWorldTextView)
        val helloWorldTextView = binding.helloWorldTextView
        helloWorldTextView.text = getString(R.string.good_bye_string)
//        change the txt property
        helloWorldTextView.text = getString(R.string.name_string)
    }
}