package com.mybatis.pj.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import com.mybatis.pj.example.update.UpdateBaseExample;
import com.mybatis.pj.provider.MyUpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface UpdateBaseMapper {
	
	@UpdateProvider(type=MyUpdateProvider.class,method="dynamicSQL")
	public int updateObject( UpdateBaseExample baseExample);
}
