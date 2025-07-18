package com.example.calculadora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observa los cambios de expresión y resultado
        viewModel.expression.observe(this) { expression ->
            binding.tvExpression.text = expression
        }

        viewModel.result.observe(this) { result ->
            binding.tvResult.text = result
        }

        setButtonListeners()
    }

    private fun setButtonListeners() = with(binding) {
        // Botones numéricos
        listOf(
            btn0, btn1, btn2, btn3, btn4,
            btn5, btn6, btn7, btn8, btn9
        ).forEach { button ->
            button.setOnClickListener {
                viewModel.appendSymbol(button.text.toString())
            }
        }

        // Botones de operadores
        listOf(
            btnAdd, btnSubtract, btnMultiply, btnDivide
        ).forEach { button ->
            button.setOnClickListener {
                viewModel.appendSymbol(button.text.toString())
            }
        }

        // Punto decimal
        btnDot.setOnClickListener {
            viewModel.appendSymbol(".")
        }

        // Igual
        btnEquals.setOnClickListener {
            viewModel.calculate()
        }

        // Borrar todo
        btnClear.setOnClickListener {
            viewModel.clear()
        }

        // Borrar último carácter
        btnDelete.setOnClickListener {
            viewModel.deleteLast()
        }
    }
}

