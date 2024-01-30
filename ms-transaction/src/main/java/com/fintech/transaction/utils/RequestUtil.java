package com.fintech.transaction.utils;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RequestUtil {

    private RequestUtil(){
        throw new UnsupportedOperationException("Can't instantiate a util class!");
    }

    public static String getRequestPath(){
        return getRequest().getRequestURI();
    }

    private static HttpServletRequest getRequest(){
        return ( (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
