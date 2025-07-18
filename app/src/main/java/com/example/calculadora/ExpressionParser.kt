package com.example.calculadora

class ExpressionParser {

    fun evaluate(expression: String): Double {
        return parse(expression)
    }

    private fun parse(expr: String): Double {
        val tokens = tokenize(expr)
        val postfix = toPostfix(tokens)
        return evaluatePostfix(postfix)
    }

    private fun tokenize(expr: String): List<String> {
        val regex = Regex("([+\\-*/])|([0-9.]+)")
        return regex.findAll(expr).map { it.value }.toList()
    }

    private fun toPostfix(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val stack = mutableListOf<String>()
        val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

        for (token in tokens) {
            if (token.toDoubleOrNull() != null) {
                output.add(token)
            } else {
                while (stack.isNotEmpty() && precedence[token]!! <= precedence[stack.last()]!!) {
                    output.add(stack.removeAt(stack.lastIndex))
                }
                stack.add(token)
            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.removeAt(stack.lastIndex))
        }

        return output
    }

    private fun evaluatePostfix(postfix: List<String>): Double {
        val stack = mutableListOf<Double>()

        for (token in postfix) {
            when {
                token.toDoubleOrNull() != null -> stack.add(token.toDouble())
                else -> {
                    val b = stack.removeAt(stack.lastIndex)
                    val a = stack.removeAt(stack.lastIndex)
                    val result = when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> a / b
                        else -> throw IllegalArgumentException("Operador no válido")
                    }
                    stack.add(result)
                }
            }
        }

        return stack.last()
    }
}

