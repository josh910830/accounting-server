package com.github.suloginscene.accountant.context.common.exception;


public class ForbiddenException extends RuntimeException {

    public ForbiddenException(Long memberId, Object resource) {
        super(memberId + ", " + resource);
    }

}
