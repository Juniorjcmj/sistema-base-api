package com.jcmj.resource.exceptions;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jcmj.service.exception.DataIntegrityViolationException;
import com.jcmj.service.exception.ObjectnotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectnotFoundException.class)
	public ResponseEntity<StandartError> objectnotFoundException(ObjectnotFoundException ex, HttpServletRequest request){
		
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value()
				                                , "Object Not Found", ex.getMessage(), request.getRequestURI());		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandartError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
		
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value()
				                                , "Violação de dados", ex.getMessage(), request.getRequestURI());		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
}
