package org.grails.plugins.defaultExceptionHandler.exception

public class NotFoundException extends RuntimeException {

    def id

    public NotFoundException(id){
        this(id, "Can't found someting with id " + id)
    }

    public NotFoundException(id, Class clazz){
        this(id, "Can't found " + clazz.getSimpleName() +  " with id " + id)
    }

    public NotFoundException(id, String message) {
        super(message)
        this.id = id
    }

    def getId(){
        return id
    }


}
