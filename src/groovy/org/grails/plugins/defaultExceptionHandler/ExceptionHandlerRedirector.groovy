package org.grails.plugins.defaultExceptionHandler

import org.codehaus.groovy.grails.commons.ApplicationHolder
import groovy.util.logging.Commons

@Commons // for logging
class ExceptionHandlerRedirector {
	
	public static void processException(Exception exception, controller, action) {
		def ctx = ApplicationHolder.getApplication().getMainContext();
		ExceptionHandler handler = ctx.getBean("defaultExceptionHandlerProcessor")
		if(handler != null) {
			handler.processException(exception, controller, action) 
		} else {
			log.error("defaultExceptionHandlerProcessor bean is not defined")
		}
    }
}