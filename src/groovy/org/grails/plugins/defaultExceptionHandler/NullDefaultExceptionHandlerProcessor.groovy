package org.grails.plugins.defaultExceptionHandler

import groovy.util.logging.Commons

@Commons // for logging
public class NullDefaultExceptionHandlerProcessor implements ExceptionHandler {
	public void processException(Exception exception, controller, action) {
		throw exception
	}
}