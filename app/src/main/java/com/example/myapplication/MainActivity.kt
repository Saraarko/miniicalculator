package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextView: TextView
    private lateinit var oldInputTextView: TextView
    private var currentInput: String = ""
    private var previousValue: String = ""
    private var currentValue: String = ""
    private var currentOperator: String = ""
    private var isNewEntry: Boolean = true

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.inout)
        oldInputTextView = findViewById(R.id.output)

        val numberButtons = (0..9).map { findViewById<Button>(resources.getIdentifier("button_$it", "id", packageName)) }

        numberButtons.forEach { button ->
            button.setOnClickListener { onNumberButtonClick(button) }
        }

        findViewById<Button>(R.id.button_dot).setOnClickListener { onDotButtonClick() }
        listOf(
            R.id.button_addition to "+",
            R.id.button_subtraction to "-",
            R.id.button_multiply to "*",
            R.id.button_division to "/"
        ).forEach { (id, operator) ->
            findViewById<Button>(id).setOnClickListener { setOperator(operator) }
        }

        findViewById<Button>(R.id.button_clear).setOnClickListener { inputTextView.text = "" }
        findViewById<Button>(R.id.button_croxx).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.button_equals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.button_bracket).setOnClickListener { inputTextView.append("(") }
        findViewById<Button>(R.id.button_bracket_r).setOnClickListener { inputTextView.append(")") }
    }

    private fun onNumberButtonClick(button: Button) {
        if (isNewEntry) {
            inputTextView.text = ""
            isNewEntry = false
        }
        inputTextView.append(button.text)
    }

    private fun onDotButtonClick() {
        if (isNewEntry) {
            inputTextView.text = "0."
            isNewEntry = false
        } else if (!inputTextView.text.contains(".")) {
            inputTextView.append(".")
        }
    }

    private fun setOperator(op: String) {
        previousValue = inputTextView.text.toString()
        currentOperator = op
        oldInputTextView.text = "$previousValue $currentOperator"
        inputTextView.text = ""
        isNewEntry = true
    }

    private fun calculateResult() {
        currentValue = inputTextView.text.toString()
        val result = performCalculation()
        inputTextView.text = result.toString()
        oldInputTextView.text = ""
        isNewEntry = true
    }

    private fun performCalculation(): Double {
        return when (currentOperator) {
            "+" -> previousValue.toDouble() + currentValue.toDouble()
            "-" -> previousValue.toDouble() - currentValue.toDouble()
            "*" -> previousValue.toDouble() * currentValue.toDouble()
            "/" -> previousValue.toDouble() / currentValue.toDouble()
            else -> 0.0
        }
    }

    private fun clearAll() {
        inputTextView.text = ""
        oldInputTextView.text = ""
        previousValue = ""
        currentValue = ""
        currentOperator = ""
    }
}
