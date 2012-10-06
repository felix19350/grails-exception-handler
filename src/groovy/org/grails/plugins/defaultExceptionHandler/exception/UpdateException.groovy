package org.grails.plugins.defaultExceptionHandler.exception

public class UpdateException extends DomainClassException {

  public UpdateException(domainClass){
    super(domainClass, "Can't update " + domainClass.getClass().getSimpleName())
  }


  String getErrorsString() {
      String result = "id = ${domainClass.ident()}\n" 
      result += super.getErrorsString()
      return result
  }

}
