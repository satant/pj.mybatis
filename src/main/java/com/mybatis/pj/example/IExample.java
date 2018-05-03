package com.mybatis.pj.example;


import com.mybatis.pj.exception.ExampleException;

/**
 * @author satant
 * @Description 通用方法的基层接口
 * @date Created in 8:56 2018/3/28
 * @Modified By
 */
@FunctionalInterface
public interface IExample {
    public void end() throws ExampleException;
}
