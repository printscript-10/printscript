package interpreter.nodeInterpreter

import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable

sealed interface BinaryFunction {
    fun execute(left: Variable, right: Variable): Variable
}

data object Sum : BinaryFunction {
    override fun execute(left: Variable, right: Variable): Variable {
        if (left is NumericVariable && right is NumericVariable) return left + right
        return StringVariable(left.value.toString() + right.value.toString())
    }
}

data object Subtraction : BinaryFunction {
    override fun execute(left: Variable, right: Variable): NumericVariable {
        return (left as NumericVariable) - (right as NumericVariable)
    }
}

data object Multiplication : BinaryFunction {
    override fun execute(left: Variable, right: Variable): NumericVariable {
        return (left as NumericVariable) * (right as NumericVariable)
    }
}

data object Division : BinaryFunction {
    override fun execute(left: Variable, right: Variable): NumericVariable {
        return (left as NumericVariable) / (right as NumericVariable)
    }
}
