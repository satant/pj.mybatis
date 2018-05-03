package com.mybatis.pj.example.update.multipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis.pj.example.update.UpdateBaseExample;
import com.mybatis.pj.exception.ExampleException;


public class MultipartUpdateExample extends UpdateBaseExample  {

	Logger log = LoggerFactory.getLogger(MultipartUpdateExample.class);
	public MultipartUpdateExample(String tableName) {
		super(tableName);
	}


	@Override
	public MultipartUpdateExample addUpdateField(String field, Object newValue) {
		super.addUpdateField(field, newValue);
		return this;
	}


	/**
	 * 开始查询前结束时调用的检查方法
	 * @throws ExampleException
	 */
	@Override
	public void end() throws ExampleException {
		super.end();
	}

}
