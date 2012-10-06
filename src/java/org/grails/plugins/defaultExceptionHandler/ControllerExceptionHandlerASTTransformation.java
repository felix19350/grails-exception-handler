package org.grails.plugins.defaultExceptionHandler;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.Arrays;
import java.util.List;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class ControllerExceptionHandlerASTTransformation implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes.length != 2 ||
                !(nodes[0] instanceof AnnotationNode) ||
                !(nodes[1] instanceof AnnotatedNode)) {
            System.err.println("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes));
        } else {
            MethodNode method = (MethodNode) nodes[1];

            ModuleNode moduleNode = sourceUnit.getAST();
            
            System.out.println("Adding controller exception handler to " + moduleNode.getMainClassName() + " - " + method.getName());

            List<Statement> existingStatements = ((BlockStatement)method.getCode()).getStatements();

            Statement tryCatchStatement = createTryCatchStatement(existingStatements, method.getName());
            existingStatements.clear();
            existingStatements.add(tryCatchStatement);
        }

    }

    /**
     * Create try/catch block:
     *
     * try {
     *      [ExistingStatements...]
     * } catch(Exception ex) {
     *      ControllerUtils.renderDomainClassException(this, ex)
     * }
     */
    private Statement createTryCatchStatement(List<Statement> existingStatements, String actionName) {
        BlockStatement tryStatement = new BlockStatement();
        tryStatement.addStatements(existingStatements);

        TryCatchStatement tryCatchStatement = new TryCatchStatement(
                tryStatement,
                EmptyStatement.INSTANCE
        );


        tryCatchStatement.addCatch(createCatchStatement(actionName));

        return tryCatchStatement;
    }


    /**
     * Create catch block:
     *
     * catch(Exception ex) {
     *      ControllerUtils.renderDomainClassException(this, ex, action)
     * }
     */
    private CatchStatement createCatchStatement(String actionName) {
        BlockStatement code = new BlockStatement();
        ArgumentListExpression args = new ArgumentListExpression();

        args.addExpression(new VariableExpression("ex"));
        args.addExpression(new VariableExpression("this"));
        args.addExpression(new ConstantExpression(actionName));

        code.addStatement(new ExpressionStatement(new MethodCallExpression(
                new ClassExpression(new ClassNode(ExceptionHandlerRedirector.class)),
                "processException",
                args)));

        return new CatchStatement(new Parameter(new ClassNode(Exception.class), "ex"), code);

    }
}
