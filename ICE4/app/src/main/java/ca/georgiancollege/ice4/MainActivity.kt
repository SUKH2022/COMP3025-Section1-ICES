package ca.georgiancollege.ice4

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgiancollege.ice4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
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
        // Add listeners for operator buttons
        val operatorButtons = listOf(
            binding.clearButton, binding.percentButton, binding.deleteButton,
            binding.divideButton, binding.mulitplyButton, binding.minusButton,
            binding.plusButton, binding.equalsButton
        )
        setButtonClickListeners(operatorButtons, this::operatorButtonClicked)

        // Add listeners for number buttons
        val numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton,
            binding.threeButton, binding.fourButton, binding.fiveButton,
            binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.decimalButton, binding.plusMinusButton
        )
        setButtonClickListeners(numberButtons, this::numberButtonClicked)
    }

    // Function to set click listeners for a list of buttons
    private fun setButtonClickListeners(buttons: List<Button>, clickHandler: (Button) -> Unit) {
        buttons.forEach { button ->
            button.setOnClickListener {
                clickHandler(button)
            }
        }
    }

    // Click handler for operator buttons
    private fun operatorButtonClicked(button: Button) {
        val tag = button.tag.toString()
        binding.resultTextView.text = tag
    }

    // Click handler for number buttons
    private fun numberButtonClicked(button: Button) {
        val tag = button.tag.toString()
        binding.resultTextView.text = tag
    }
}