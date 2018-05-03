package com.mybatis.pj.exception.example;

import com.mybatis.pj.exception.ExampleException;

/**
 * Created with IntelliJ IDEA.
 * User: satant
 * Date: 2018/3/28
 * Time: 9:26
 * Description:
 */
public class NoUpdateFieldException extends ExampleException{

    public NoUpdateFieldException(){

    }

    public NoUpdateFieldException(String message){
        super(message);
    }
}