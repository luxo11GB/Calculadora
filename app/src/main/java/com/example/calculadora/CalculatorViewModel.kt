package com.example.calculadora

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CalculatorViewModel : ViewModel() {

    private val _expression = MutableLiveData<String>("")
    val expression: LiveData<String> = _expression

    private val _result = MutableLiveData<String>("0")
    val result: LiveData<String> = _result

    fun appendSymbol(symbol: String) {
        _expression.value += symbol
    }

    fun clear() {
        _expression.value = ""
        _result.value = "0"
    }

    fun deleteLast() {
        _expression.value = _expression.value?.dropLast(1)
    }

    fun calculate() {
        try {
            val expr = _expression.value ?: ""
            val resultValue = evaluateExpression(expr)
            _result.value = resultValue.toString()
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }

    private fun evaluateExpression(expr: String): Double {
        // Quita espacios, reemplaza "×", "÷" si los tuvieras
        val sanitized = expr.replace("×", "*").replace("÷", "/")

        // Esta función es simple: usa ScriptEngine-like parsing con la clase built-in
        return ExpressionParser().evaluate(sanitized)
    }
}
