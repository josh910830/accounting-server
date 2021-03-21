package com.github.suloginscene.accountant.context.common.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> targetClass, Object id) {
        super(targetClass.getSimpleName() + ":" + id);
    }

}
