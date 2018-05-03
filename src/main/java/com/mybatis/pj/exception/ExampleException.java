package com.mybatis.pj.exception;

@SuppressWarnings("serial")
public class ExampleException extends RuntimeException{

    public ExampleException(){

    }
    public ExampleException(String message) {
        super(message);
    }
}
