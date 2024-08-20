package parser.semanticAnalizer

import utils.AST
import utils.PrintFunction
import utils.Type
import utils.VariableDeclaration

class SemanticAnalizer {

    private val context: Map<String,String> = mapOf()


    fun analize(ast: AST): Boolean{

        when(ast){
            //is VariableDeclaration -> checkVariableDeclaration(variables).execute(ast)
            // is Assignation -> AssignationInterpreter(variables).execute(ast)
            else-> throw Error("${ast.position} is not a valid sentence")
        }

        return true
    }
}
