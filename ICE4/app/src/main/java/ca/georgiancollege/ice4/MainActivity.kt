package ca.georgiancollege.ice4

import android.os.Bundle
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

        // It is an array of buttons representing operands (numbers, decimal, delete, clear, plus/minus)
        val operandButtons = arrayOf(
            binding.oneButton, binding.twoButton, binding.threeButton, binding.fourButton,
            binding.fiveButton, binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.zeroButton, binding.decimalButton, binding.deleteButton,
            binding.plusMinusButton, binding.clearButton
        )

        // It is an array of buttons representing operators (multiply, plus, percent, minus, equals, divide)
        val operatorButtons = arrayOf(
            binding.mulitplyButton, binding.plusButton, binding.percentButton, binding.minusButton,
            binding.equalsButton, binding.divideButton
        )

        // It loops through each button and sets its onClickListener to call the Handlers
        // function with the button's tag which is the button text.
        operandButtons.forEach{it.setOnClickListener{operandHandler(it.tag as String)}}
        operatorButtons.forEach{it.setOnClickListener{operatorHandler(it.tag as String)}}
    }

    // Variable to stores the operand
    private var currentOperand = ""

    // func. to handle clicks on buttons like numbers, decimal, delete, clear, & plus/minus
    private fun operandHandler(tag: String)
    {
        when (tag) {
            // Clear button
            "clearButton" -> {
                currentOperand = if (currentOperand == "-0") "0" else ""
            }

            // delete button
            "deleteButton" -> {
                if (currentOperand.isNotEmpty()) {
                    currentOperand = currentOperand.substring(0, currentOperand.length - 1);
                }
            }
            // plus minus button
            "plusMinusButton" -> {
                currentOperand = if (currentOperand.startsWith("-")) {
                    currentOperand.substring(1);
                } else {
                    "-$currentOperand";
                }
            }
            else -> {
                if ((currentOperand != "0" || tag == "0").not()) {
                    currentOperand = "";
                }
                currentOperand += tag;
            }
        }
        binding.resultTextView.text = currentOperand;
    }

    private fun operatorHandler(tag: String)
    {
        binding.resultTextView.text = tag
    }
}