package com.mybatis.pj.provider;

import org.apache.ibatis.mapping.MappedStatement;

import com.mybatis.pj.util.MyExampleSqlHelp;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class MyUpdateProvider extends MapperTemplate{

	public MyUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	public String updateObject(MappedStatement ms) {
		StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(MyExampleSqlHelp.getUpdateColumn());
        sql.append(MyExampleSqlHelp.useWhereAndEqualsWhere());
        sql.append(MyExampleSqlHelp.useGreaterThan());
        sql.append(MyExampleSqlHelp.useLessThan());
        sql.append(MyExampleSqlHelp.useNotEquals());
        sql.append(MyExampleSqlHelp.notUseWhere());
        sql.append(MyExampleSqlHelp.useIn());
        sql.append(MyExampleSqlHelp.useLike());
        return sql.toString();
	}
	
	
}
