package ca.georgiancollege.ice3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgiancollege.ice3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // instantiates an object of type ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        //sets the content view to the "super view" or main view grp
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // this line created the reference to the TxtView
        val helloWorldTextView = binding.helloWorldTextView
        helloWorldTextView.text = getString(R.string.good_bye_string)
        // change the txt property
        helloWorldTextView.text = getString(R.string.name_string)

        binding.clickMeButton.setOnClickListener{
            sharedEventHandler(it as Button)
            // display Log message for debugging
            Log.i("onCreate", "Click Me Button Clicked")
        }

        binding.anotherButton.setOnClickListener {
            sharedEventHandler(it as Button)
            Log.i("onCreate","Another Button was Clicked!")
        }
    }
    // This function is used to toggle b/w buttons with Kotlin
//    private fun sharedEventHandler(button: Button) {
    private fun sharedEventHandler(button: Button) = when(button)
    {
        binding.clickMeButton -> binding.helloWorldTextView.text =
            (if(binding.helloWorldTextView.text == "Clicked!")
                getString(R.string.not_clicked)
            else
                getString(R.string.clicked))

        binding.anotherButton ->
            binding.helloWorldTextView.text = getString(R.string.do_something_else)

        else -> {}
//          casting the button
//        val button = view as button

//        when(button){
//            binding.clickMeButton -> {
//
//            }
//            binding.anotherButton ->{
//
//            }
        }
    }