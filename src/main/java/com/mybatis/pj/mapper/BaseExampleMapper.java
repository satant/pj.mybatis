package com.mybatis.pj.mapper;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author satant
 */
public interface BaseExampleMapper<T> extends Mapper<T>,SelectBaseMapper<T>,UpdateBaseMapper{
	
}
