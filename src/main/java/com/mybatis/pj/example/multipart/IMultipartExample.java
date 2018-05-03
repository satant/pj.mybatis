package com.mybatis.pj.example.multipart;

import com.mybatis.pj.exception.ExampleException;


@Deprecated
public interface IMultipartExample {







    void end() throws ExampleException;






//	/**
//     * 链式操作添加in()的数值
//     * @param inValue 要查找的数值
//     * @return this对象
//     */
//    public BaseExample in(String inValue);
//
//    /**
//     * 添加in查询所需字段方法 类似 infield  in （value1,value2,value3...）
//     * @param infield 字段名
//     * @return
//     */
//    public BaseExample addInField(String infield);
//
//    /**
//     * 添加like查询所需的字段和包含值
//     * 			类似：  field  like  "%likeValue%"
//     * @param field 字段名
//     * @param likeValue 包含值
//     * @return
//     */
//    public BaseExample like(String field , String likeValue);
}
