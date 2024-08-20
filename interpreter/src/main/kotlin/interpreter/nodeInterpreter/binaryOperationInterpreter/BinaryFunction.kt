package interpreter.nodeInterpreter.binaryOperationInterpreter

import interpreter.Variable

// este nombre es malisimo
sealed interface BinaryFunction{
    fun execute(left: Variable, right: Variable): Variable
}

class Sum: BinaryFunction {
    override fun execute(left: Variable, right: Variable): Variable {
        // hay q validar esto? en teoria ya deberia validarse en el Lexeo/Parseo
        if(left.value == null || right.value == null){
            throw Error("Sum cannot have empty parameters")
        }

        if(left.type == "number" && right.type == "number"){
            return Variable(type= "number", value = (left.value.toDouble() + right.value.toDouble()).toString())
        }
        return Variable(type= "string", value = (left.value + right.value))
    }

}

class Subtraction: BinaryFunction{
    override fun execute(left: Variable, right: Variable): Variable {
        if(left.value == null || right.value == null){
            throw Error("Sum cannot have empty parameters")
        }
        if(left.type != "number" && right.type != "number"){
            throw Error("Subtraction can only take numbers")
        }
        return Variable(type= "number", value = (left.value.toDouble() - right.value.toDouble()).toString())
    }

}

class Multiplication: BinaryFunction{
    override fun execute(left: Variable, right: Variable): Variable {
        if(left.value == null || right.value == null){
            throw Error("Sum cannot have empty parameters")
        }
        if(left.type != "number" && right.type != "number"){
            throw Error("Subtraction can only take numbers")
        }
        return Variable(type= "number", value = (left.value.toDouble() * right.value.toDouble()).toString())
    }

}

class Division: BinaryFunction{
    override fun execute(left: Variable, right: Variable): Variable {
        if(left.value == null || right.value == null){
            throw Error("Sum cannot have empty parameters")
        }
        if(left.type != "number" && right.type != "number"){
            throw Error("Subtraction can only take numbers")
        }
        return Variable(type= "number", value = (left.value.toDouble() / right.value.toDouble()).toString())
    }

}
