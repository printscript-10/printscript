package interpreter.nodeInterpreter.binaryOperationInterpreter

import interpreter.Variable

sealed interface BinaryFunction {
    fun execute(left: Variable, right: Variable): Variable
}

class Sum : BinaryFunction {
    override fun execute(left: Variable, right: Variable): Variable {
        if (left.value == null || right.value == null) {
            throw Error("Sum cannot have empty parameters")
        }

        if (left.type == "number" && right.type == "number") {
            return Variable(type = "number", value = (left.value.toDouble() + right.value.toDouble()).toString())
        }
        return Variable(type = "string", value = (left.value + right.value))
    }
}

class Subtraction : BinaryFunction {
    override fun execute(left: Variable, right: Variable): Variable {
        if (left.value == null || right.value == null) {
            throw Error("Subtraction cannot have empty parameters")
        }
        if (left.type != "number" && right.type != "number") {
            throw Error("Subtraction can only take numbers")
        }
        return Variable(type = "number", value = (left.value.toDouble() - right.value.toDouble()).toString())
    }
}

class Multiplication : BinaryFunction {
    override fun execute(left: Variable, right: Variable): Variable {
        if (left.value == null || right.value == null) {
            throw Error("Multiplication cannot have empty parameters")
        }
        if (left.type != "number" && right.type != "number") {
            throw Error("Multiplication can only take numbers")
        }
        return Variable(type = "number", value = (left.value.toDouble() * right.value.toDouble()).toString())
    }
}

class Division : BinaryFunction {
    override fun execute(left: Variable, right: Variable): Variable {
        if (left.value == null || right.value == null) {
            throw Error("Division cannot have empty parameters")
        }
        if (left.type != "number" && right.type != "number") {
            throw Error("Division can only take numbers")
        }
        return Variable(type = "number", value = (left.value.toDouble() / right.value.toDouble()).toString())
    }
}
