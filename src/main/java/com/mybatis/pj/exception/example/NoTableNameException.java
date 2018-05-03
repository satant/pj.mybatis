package com.mybatis.pj.exception.example;

import com.mybatis.pj.exception.ExampleException;

/**
 * Created with IntelliJ IDEA.
 * User: satant
 * Date: 2018/3/28
 * Time: 9:15
 * Description:
 */
public class NoTableNameException extends ExampleException{

    public NoTableNameException(){

    }

    public NoTableNameException(String message){
        super(message);
    }
}