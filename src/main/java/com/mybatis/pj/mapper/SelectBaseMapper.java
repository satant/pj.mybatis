package com.mybatis.pj.mapper;

import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;
import com.mybatis.pj.example.BaseExample;
import com.mybatis.pj.provider.MySelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface SelectBaseMapper<T> {
	
	@SelectProvider(type=MySelectProvider.class,method = "dynamicSQL")

	List<T> selectPage(BaseExample baseExample);
}