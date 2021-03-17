package com.github.suloginscene.accountant.context.common.exception;


public class NullTransientFieldException extends NullPointerException {

    public NullTransientFieldException(Class<?> filedClass, String fieldName) {
        super(filedClass.getSimpleName() + ": " + fieldName);
    }

}
