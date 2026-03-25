package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""
    private var operator: String? = null
    private var operand1: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btnDot
        )

        val operatorButtons = listOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val text = (it as Button).text
                if (!(text == "." && currentInput.contains("."))) { // prevent multiple dots
                    currentInput += text
                    updateDisplay()
                }
            }
        }

        operatorButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    operand1 = currentInput.toDouble()
                    operator = (it as Button).text.toString()
                    currentInput = ""
                }
            }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            if (operator != null && currentInput.isNotEmpty() && operand1 != null) {
                val result = calculate(operand1!!, currentInput.toDouble(), operator!!)
                currentInput = formatResult(result)
                updateDisplay()
                operator = null
                operand1 = null
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentInput = ""
            operator = null
            operand1 = null
            updateDisplay()
        }

        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = if (currentInput.startsWith("-")) {
                    currentInput.substring(1)
                } else {
                    "-$currentInput"
                }
                updateDisplay()
            }
        }

        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                val value = currentInput.toDouble() / 100
                currentInput = formatResult(value)
                updateDisplay()
            }
        }
    }

    private fun updateDisplay() {
        display.text = if (currentInput.isEmpty()) "0" else currentInput
    }

    private fun calculate(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "×" -> a * b
            "÷" -> a / b
            else -> 0.0
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1 == 0.0) value.toInt().toString() else value.toString()
    }
}