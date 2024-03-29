package org.grails.plugins.defaultExceptionHandler;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@GroovyASTTransformationClass({"org.grails.plugins.defaultExceptionHandler.ControllerExceptionHandlerASTTransformation"})
public @interface DefaultExceptionHandler {

}