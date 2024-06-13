package ca.georgiancollege.ice6

import android.annotation.SuppressLint
import ca.georgiancollege.ice6.databinding.ActivityMainBinding
import kotlin.text.*

class Calculator(dataBinding: ActivityMainBinding) {
    /* Simple Calculations: 1 + 2 = 3
    operand1 [operator] operand2 [equalsOperator] result
    LHS [operator] RHS [equalsOperator] result */

    /**
     * This variable stores the current displayed result on the calculator screen.
     */

    private val binding: ActivityMainBinding = dataBinding
    private var result: String

    // This variable stores the operands and operator entered by the user.
    private var currentOperand1: String? = null
    private var currentOperand2: String? = null
    private var currentOperator: String? = null

    /**
     * This function initializes the calculator by setting the result to an empty string
     * and calling the createButtonReferences function.
     */

    init {
        result = ""
        createButtonReferences()
    }

    /**
     * This function creates references to all the operand and operator buttons on the calculator
     * and assigns them click listeners to handle user input.
     */

    private fun createButtonReferences() {
        val operandButtons = arrayOf(
            binding.oneButton, binding.twoButton, binding.threeButton, binding.fourButton,
            binding.fiveButton, binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.zeroButton, binding.decimalButton, binding.deleteButton,
            binding.plusMinusButton
        )

        val operatorButtons = arrayOf(
            binding.mulitplyButton, binding.plusButton, binding.percentButton, binding.minusButton,
            binding.equalsButton, binding.divideButton, binding.clearButton
        )

        operandButtons.forEach { it.setOnClickListener { operandHandler(it.tag as String) } }
        operatorButtons.forEach { it.setOnClickListener { operatorHandler(it.tag as String) } }
    }

    /**
     * This function handles clicks on operand buttons (0-9, ., +/-). It updates the result
     * variable and displays the updated value on the screen.
     *
     * @param [tag] The text displayed on the button that was clicked.
     */

    private fun operandHandler(tag: String) {
        when (tag) {
            "." -> {
                if (!binding.resultTextView.text?.contains(".")!!) {
                    result += if (result.isEmpty()) "0." else "."
                    binding.resultTextView.text = result
                }
            }

            "Delete" -> {
                result = result.dropLast(1)
                binding.resultTextView.text = if (result.isEmpty() || result == "-") "0" else result
            }

            "Plus_Minus" -> {
                if (result.startsWith("-")) {
                    result = result.substring(1)
                } else {
                    if (result.isNotEmpty()) {
                        result = "-".plus(result)
                    }
                }
                binding.resultTextView.text = result
            }

            else -> {
                if (binding.resultTextView.text == "0") {
                    result = tag
                } else {
                    result += tag
                }
                binding.resultTextView.text = result
            }
        }
    }

    /**
     * This function handles clicks on operator buttons (+, -, *, /, =, C). It performs the
     * selected operation on the current operands and displays the result.
     *
     * @param [tag] The text displayed on the button that was clicked.
     */

    private fun operatorHandler(tag: String) {
        when (tag) {
            "clear" -> clear()
            "equal" -> calculateResult()
            else -> {
                if (result.isNotEmpty()) {
                    if (currentOperand1 == null) {
                        currentOperand1 = result
                        currentOperator = tag
                        result = ""
                    } else if (currentOperand2 == null) {
                        currentOperand2 = result
                        calculateResult()
                        currentOperand1 = binding.resultTextView.text.toString()
                        currentOperator = tag
                        result = ""
                    }
                }
            }
        }
    }

    /**
     * This function calculates the result of the current expression based on the stored operands
     * and operator. It updates the display with the formatted result and clears the stored operands
     * for the next calculation.
     *
     */

    private fun calculateResult() {
        if (currentOperand1 != null && currentOperator != null) {
            currentOperand2 = result
            val resultValue = when (currentOperator) {
                "plus" -> currentOperand1!!.toDouble() + currentOperand2!!.toDouble()
                "minus" -> currentOperand1!!.toDouble() - currentOperand2!!.toDouble()
                "multiple" -> currentOperand1!!.toDouble() * currentOperand2!!.toDouble()
                "divide" -> currentOperand1!!.toDouble() / currentOperand2!!.toDouble()
                else -> 0.0
            }
            binding.resultTextView.text = formatResult(resultValue)
            clearOperands()
        }
    }

    /**
     * This function clears the calculator display and resets all stored operands and operator.
     */
    private fun clear() {
        result = ""
        binding.resultTextView.text = "0"
        clearOperands()
    }

    /**
     * This function clears the stored operands and operator, effectively resetting the calculator
     * for the next calculation.
     */
    private fun clearOperands() {
        currentOperand1 = null
        currentOperand2 = null
        currentOperator = null
    }

    //Format the result if the result is an integer
    @SuppressLint("DefaultLocale")
    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else String.format("%.8f", value).trimEnd('0')
            .trimEnd('.')
    }
}