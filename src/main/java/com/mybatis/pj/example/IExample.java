package com.mybatis.pj.example;


import com.mybatis.pj.exception.ExampleException;

/**
 * @author satant
 */
@FunctionalInterface
public interface IExample {
    public void end() throws ExampleException;
}
