package com.github.suloginscene.accountant.lib.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> targetClass, Object id) {
        super(targetClass.getSimpleName() + ":" + id);
    }

}
