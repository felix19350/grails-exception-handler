package org.grails.plugins.defaultExceptionHandler

public interface ExceptionHandler {
	public void processException(Exception exception, controller, action) 
}