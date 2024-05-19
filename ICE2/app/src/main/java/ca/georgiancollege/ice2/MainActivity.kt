package ca.georgiancollege.ice2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgiancollege.ice2.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var isClicked = false

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

        val clickMeButton = binding.clickMeButton
        clickMeButton.setOnClickListener{
            sharedEventHandler(binding.clickMeButton)
//            Log.i("onCreate", "Click Me Button Clicked")
//            binding.helloWorldTextView.text = getString(R.string.clicked)
//            println("printing for the Click me button")
        }
        val anotherButton = binding.anotherButton
        anotherButton.setOnClickListener {
            sharedEventHandler(binding.anotherButton)
//            Log.i("onCreate","Another Button was Clicked!")
//            binding.helloWorldTextView.text = getString(R.string.name_string)
        }
    }
    fun sharedEventHandler(view: View) {
        Log.i("onCreate", "Button Clicked!")
        // it is used to toggle isClicked state from false to true
        isClicked = !isClicked
        if (isClicked) {
            binding.helloWorldTextView.text = getString(R.string.clicked)
        } else {
            binding.helloWorldTextView.text = getString(R.string.name_string)
        }
    }
}
