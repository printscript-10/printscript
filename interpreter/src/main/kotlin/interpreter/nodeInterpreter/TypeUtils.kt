package interpreter.nodeInterpreter

import ast.VariableType
import interpreter.BooleanVariable
import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import result.Result

fun isBoolean(string: String): Boolean {
    return string == "true" || string == "false"
}

fun isNumeric(string: String): Boolean {
    return string.toDoubleOrNull() != null
}

fun parseNumber(string: String): Number {
    return if (string.contains('.')) string.toDouble() else string.toInt()
}

fun getVariable(expected: VariableType, result: String): Result {
    return when (expected) {
        VariableType.STRING -> ExpressionSuccess(StringVariable(result, false))
        VariableType.BOOLEAN -> {
            if (!isBoolean(result)) {
                ExpressionFailure("Invalid boolean value")
            } else {
                ExpressionSuccess(BooleanVariable(result.toBoolean(), false))
            }
        }
        VariableType.NUMBER -> {
            if (!isNumeric(result)) {
                ExpressionFailure("Invalid number value")
            } else {
                ExpressionSuccess(NumericVariable(parseNumber(result), false))
            }
        }
        else -> ExpressionFailure("Invalid expected variable type")
    }
}
