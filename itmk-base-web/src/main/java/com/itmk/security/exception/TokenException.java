package com.itmk.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * token异常处理类
 * @author xlc
 */
public class TokenException extends AuthenticationException {
    public TokenException(String msg) {
        super(msg);
    }
}
