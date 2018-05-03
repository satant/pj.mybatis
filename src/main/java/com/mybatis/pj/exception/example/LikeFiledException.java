package com.mybatis.pj.exception.example;

import com.mybatis.pj.exception.ExampleException;

/**
 * Created with IntelliJ IDEA.
 * User: satant
 * Date: 2018/3/28
 * Time: 10:42
 * Description:
 */
public class LikeFiledException extends ExampleException {

    public LikeFiledException(){

    }

    public LikeFiledException(String message){
        super(message);
    }
}