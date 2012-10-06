package org.grails.plugins.defaultExceptionHandler.exception

import org.springframework.validation.FieldError


public class DomainClassException extends RuntimeException {

    def domainClass

    public DomainClassException(domainClass){
        this(domainClass, "An error occurred during an operation on " + domainClass.getClass().getSimpleName())
    }

    DomainClassException(domainClass, String message) {
        super(message)
        this.domainClass = domainClass
    }

    public DomainClassException(domainClass, Throwable throwable){
        super("An error occurred during an operation on " + domainClass.getClass().getSimpleName() + " caused by " +  throwable.getMessage(), throwable)
        this.domainClass = domainClass
    }

    public DomainClassException(domainClass, String message, Throwable throwable){
        super(message + " caused by " +  throwable.getMessage(), throwable)
        this.domainClass = domainClass
    }

    def getDomainClass(){
        return domainClass
    }

    def getErrors(){
        return domainClass.errors
    }

    String getErrorsString() {
        String result = ""
        if(domainClass) {
			def allErrors =  domainClass.errors.allErrors.findAll({it instanceof FieldError})
			def allErrorsString = allErrors.collect({"${it.field} = '${it.rejectedValue}' (${it.getCode()})"})
			result = allErrorsString.join("\n")
        }
        return result
    }


}
