package com.systrangroup.web.template.example.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {
	
	private static final Logger log = LoggerFactory.getLogger(RestControllerAspect.class);
	
    @Before("execution(public * com.systrangroup.web.template.example.controller.*Controller.*(..))")
    public void before(JoinPoint jp) throws Throwable {
    	log.info(":::::Before REST api call:::::" + jp);
    }
    
}

