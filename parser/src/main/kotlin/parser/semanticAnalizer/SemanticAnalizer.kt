package parser.semanticAnalizer

import utils.AST

class SemanticAnalizer {

    fun analize(ast: AST): Boolean{
        val result = ast.accept(TypeVisitor())
    }
}
