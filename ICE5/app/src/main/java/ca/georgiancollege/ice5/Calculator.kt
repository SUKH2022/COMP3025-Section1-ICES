package ca.georgiancollege.ice5

import ca.georgiancollege.ice5.databinding.ActivityMainBinding

class Calculator(dataBinding: ActivityMainBinding)
{
    private val binding: ActivityMainBinding = dataBinding
    private var result: String

    init {
        result = ""
        createButtonReferences()
    }

    private fun createButtonReferences(): Unit
    {
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

    private fun operandHandler(tag: String)
    {
        when(tag)
        {
            "." -> {
                if(!binding.resultTextView.text.contains("."))
                {
                    result += if(result.isEmpty()) "0." else "."

                    binding.resultTextView.text = result
                }
            }
            "Delete" -> {}
            "Plus_Minus" -> {}
            else -> {
                if(binding.resultTextView.text == "0")
                {
                    result = tag
                }
                else
                {
                    result += tag
                }
                binding.resultTextView.text = result
            }
        }
    }

    private fun operatorHandler(tag: String)
    {
        when(tag) {
            "Clear" -> {}
            else -> {

            }
        }
    }
}