package com.mybatis.pj.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mybatis.pj.exception.ExampleException;
import com.mybatis.pj.exception.example.InFiledException;
import com.mybatis.pj.exception.example.LikeFiledException;
import com.mybatis.pj.exception.example.NoTableNameException;


public abstract class BaseExample implements IExample{
	Logger log = LoggerFactory.getLogger(BaseExample.class);
	
	private boolean useMultipart;
	//相等条件存储
	private List<String> equalsWhereKey;
	private List<Object> equalsWhereValue;
	//大于条件存储
	private List<String> greaterThanWhereKey;
	private List<Object> greaterThanWhereValue;
	//小于条件存储
	private List<String> lessThanWhereKey;
	private List<Object> lessThanWhereValue;
	//不等于条件存储
	private List<String> notEqualsWhereKey;
	private List<Object> notEqualsWhereValue;
	//表名集合
	private List<String> tableName;
	//表名与别名的映射。key为别名，value是表名
	private Map<String,String> tableMap;
	//别名集合
	private List<String> tableAlias;

	private List<String> inFields ;
	private List<Object> inValue1 ;
	private List<Object> inValue2 ;
	private List<String> likeFields ;
	private List<String> likeValues ;
	@Override
	public void end() throws ExampleException {
		
		//如果表名为空直接抛出异常
		if(tableName == null||tableName.size() == 0 ){
			throw new NoTableNameException("使用SelectBaseExample时表名不能为空！");
		}
		//检查“=”查询时的字段与值的数量是不是一致
		if(equalsWhereKey != null && equalsWhereKey.size()>0) {
			if(equalsWhereKey.size() != equalsWhereValue.size()) {
				throw new IllegalArgumentException("“=”查询的字段与值的数量不一致！");
			}
		}
		//检查“>”查询时的字段与值的数量是不是一致
		if(greaterThanWhereKey != null && greaterThanWhereKey.size()>0) {
			if(greaterThanWhereKey.size() != greaterThanWhereValue.size()) {
				throw new IllegalArgumentException("“>”查询的字段与值的数量不一致！");
			}
		}
		//检查“<”查询时的字段与值的数量是不是一致
		if(lessThanWhereKey != null && lessThanWhereKey.size()>0) {
			if(lessThanWhereKey.size() != lessThanWhereValue.size()) {
				throw new IllegalArgumentException("“<”查询的字段与值的数量不一致！");
			}
		}
		//检查“!=”查询时的字段与值的数量是不是一致
		if(notEqualsWhereKey != null && notEqualsWhereKey.size()>0) {
			if(notEqualsWhereKey.size() != notEqualsWhereValue.size()) {
				throw new IllegalArgumentException("“!=”查询的字段与值的数量不一致！");
			}
		}
		//如果字段为空，则赋默认值*，此步骤在selectBaseExample中执行
		//判断别名映射的数量是否与表名集合数量一致
		if(tableAlias != null){
			if(tableAlias.size() != tableName.size()){
				throw new IllegalArgumentException("使用别名时，别名数量与表名不一致，请检查！");
			}
		}
		if( inFields == null && likeFields == null){
			if(inValue1 != null && inValue1.size() != 0){
				throw new InFiledException("在没有inField字段时不允许使用in()方法增加值。");
			}
			return ;
		}else if(inFields.size()==1 && (inValue1 == null || inValue1.size() == 0)){
			throw new InFiledException("SelectWithInExample类在inFields不为空时，必需有value与其对应！");
		}else if(inFields.size() == 2 && (inValue2 == null || inValue2.size() == 0)){
			log.debug("inFields中的字段数量与inValue中不符，删除了inFields中第二个字段");
			inFields.remove(1);
		}
		inFields.forEach(str -> {
			if(StringUtils.isBlank(str)){
				throw new InFiledException("inField字段不能为空。");
			}
		});
		if(likeFields != null && likeValues != null) {
			if (likeFields.size() != likeValues.size()) {
				throw new LikeFiledException("like查询时的字段与值的数量不相等！");
			}
		}
		if((likeFields ==null && likeValues != null )||(likeFields !=null && likeValues == null )){
			throw new IllegalArgumentException("likeField 和 likeValue必须成对出现！！");
		}
	}




	/**
	 * 链式操作添加in()查新的包含值 调用此方法前应先调用过addInField方法
	 * @param inValue 要查找的数值
	 * @return this对象
	 */
	public BaseExample in(String inValue){
		if(inFields == null) {
			throw new InFiledException("在执行in()方法前请先执行addInField()方法增加要查询的字段。");
		}
		int size = inFields.size();
		if(size == 1){
			if(this.inValue1 == null || this.inValue1.size() ==0){
				this.inValue1 = new ArrayList<>();
			}
			this.inValue1.add(inValue);
		}else if(size ==2){
			if(this.inValue2 == null || this.inValue2.size() ==0){
				this.inValue2 = new ArrayList<>();
			}
			this.inValue2.add(inValue);
		}
		return this;
	}

	/**
	 * 链式操作添加in()查新的包含值 调用此方法前应先调用过addInField方法
	 * @param inValue 要查找的数值的list集合
	 * @return this对象
	 */
	public BaseExample in(List<String> inValue){
		if(inFields == null) {
			throw new InFiledException("在执行in()方法前请先执行addInField()方法增加要查询的字段。");
		}
		int size = inFields.size();
		if(size == 1){
			if(this.inValue1 == null || this.inValue1.size() ==0){
				this.inValue1 = new ArrayList<>();
			}
			this.inValue1.addAll(inValue);
		}else if(size ==2){
			if(this.inValue2 == null || this.inValue2.size() ==0){
				this.inValue2 = new ArrayList<>();
			}
			this.inValue2.addAll(inValue);
		}
		return this;
	}



	/**
	 * 添加in查询所需字段方法 类似 infield  in （value1,value2,value3...）
	 * @param infield 字段名
	 * @return 返回BaseExample对象
	 */
	public BaseExample addInField(String infield){
		if(this.inFields == null || this.inFields.size() == 0){
			this.inFields = new ArrayList<>();
		}
		this.inFields.add(infield);
		return this;
	}




	/**
	 * 添加like查询所需的字段和包含值
	 * 			类似：  field  like  "%likeValue%"
	 * @param field 字段名
	 * @param likeValue 包含值
	 * @return 返回BaseExample对象
	 */
	public BaseExample like(String field, String likeValue){
		if(this.likeFields == null || this.likeFields.size() == 0){
			this.likeFields = new ArrayList<>();
		}
		if(this.likeValues == null || this.likeValues.size() == 0){
			this.likeValues = new ArrayList<>();
		}
		this.likeFields.add(field);
		this.likeValues.add(likeValue);
		return this;
	}


	/**
	 * 相等的where条件
	 * @param field 字段名
	 * @param value 值
	 * @return 返回BaseExample对象
	 */
	public BaseExample equalsWhere(String field,Object value){
		if(this.equalsWhereKey == null){
			equalsWhereKey = new LinkedList<>();
			equalsWhereValue = new LinkedList<>();
		}
		equalsWhereKey.add(field);
		equalsWhereValue.add(value);
		return this;
	}
	
	/**
	 * 大于的where条件
	 * @param field 字段名
	 * @param value 值
	 * @return 返回BaseExample对象
	 */
	public BaseExample greaterThanWhere(String field,Object value){
		if(this.greaterThanWhereKey == null){
			greaterThanWhereKey = new LinkedList<>();
			greaterThanWhereValue = new LinkedList<>();
		}
		greaterThanWhereKey.add(field);
		greaterThanWhereValue.add(value);
		return this;
	}
	
	/**
	 * 小于的where条件
	 * @param field 字段名
	 * @param value 值
	 * @return 返回BaseExample对象
	 */
	public BaseExample lessThanWhere(String field,Object value){
		if(this.lessThanWhereKey == null){
			lessThanWhereKey = new LinkedList<>();
			lessThanWhereValue = new LinkedList<>();
		}
		lessThanWhereKey.add(field);
		lessThanWhereValue.add(value);
		return this;
	}	
	
	/**
	 * 不等于的where条件
	 * @param field 字段名
	 * @param value 值
	 * @return 返回BaseExample对象
	 */
	public BaseExample notEqualsWhere(String field,Object value){
		if(this.notEqualsWhereKey == null){
			notEqualsWhereKey = new LinkedList<>();
			notEqualsWhereValue = new LinkedList<>();
		}
		notEqualsWhereKey.add(field);
		notEqualsWhereValue.add(value);
		return this;
	}
	
	
	/**
	 * 增加表名
	 * @param tableName 表名
	 */
	protected void addTableName(String tableName){
		if(this.tableName == null ){
			this.tableName = new ArrayList<>();
		}
		this.tableName.add(tableName);
	}

	/**
	 * 增加别名和表名的映射关系
	 * @param alias 别名
	 * @param tableName 表名
	 * @return 返回BaseExample对象
	 */
	public BaseExample addAlias(String alias,String tableName){
		if(this.tableAlias == null){
			this.tableAlias = new ArrayList<>();
		}
		this.tableAlias.add(alias);
		if(this.tableMap == null){
			this.tableMap = new HashMap<String,String>();
		}
		this.tableMap.put(alias,tableName);
		return this;
	}



	public List<String> getTableName() {
		return tableName;
	}
	public List<String> getTableAlias() {
		return tableAlias;
	}
	public Map<String, String> getTableMap() {
		return tableMap;
	}
	public List<String> getInFields() {
		return inFields;
	}
	public List<Object> getInValue1() {
		return inValue1;
	}
	public List<Object> getInValue2() {
		return inValue2;
	}
	public List<String> getLikeFields() {
		return likeFields;
	}
	public List<String> getLikeValues() {
		return likeValues;
	}
	public boolean isUseMultipart() {
		return useMultipart;
	}
	public List<String> getEqualsWhereKey() {
		return equalsWhereKey;
	}

	public List<Object> getEqualsWhereValue() {
		return equalsWhereValue;
	}

	public List<String> getGreaterThanWhereKey() {
		return greaterThanWhereKey;
	}

	public List<Object> getGreaterThanWhereValue() {
		return greaterThanWhereValue;
	}

	public List<String> getLessThanWhereKey() {
		return lessThanWhereKey;
	}

	public List<Object> getLessThanWhereValue() {
		return lessThanWhereValue;
	}

	public List<String> getNotEqualsWhereKey() {
		return notEqualsWhereKey;
	}

	public List<Object> getNotEqualsWhereValue() {
		return notEqualsWhereValue;
	}
	public void setUseMultipart(boolean useMultipart) {
		this.useMultipart = useMultipart;
	}

}
