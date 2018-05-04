package com.mybatis.pj.example.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis.pj.example.BaseExample;
import com.mybatis.pj.exception.ExampleException;

public class SelectBaseExample extends BaseExample {
    Logger log = LoggerFactory.getLogger(SelectBaseExample.class);
    private Map<String,Object> order;
    private String groupBy;

    //此处的fields一般是表字段名的集合，但是在特殊的情况下，例如求和时可以是   sum(字段名)
    private List<String> fields;

    public SelectBaseExample(String tableName,String alias, Map<String, Object> orderBy, String groupBy) {
    	super.setUseMultipart(false);
        super.addTableName(tableName);
        super.addAlias(alias,tableName);
        this.order = orderBy;
        this.groupBy = groupBy;
    }

    public SelectBaseExample(String tableName,String alias) {
    	super.setUseMultipart(false);
        super.addTableName(tableName);
        super.addAlias(alias,tableName);
    }
    public SelectBaseExample(String tableName) {
    	super.setUseMultipart(false);
        super.addTableName(tableName);
    }

    /**
     * 表示查询语句结束，进行检查的方法
     * @throws ExampleException 异常
     */
    @Override
    public void end() throws ExampleException {
        super.end();
        //判断查询时是否有字段，若没有传入任何字段则默认查询为 "*"
        if(fields == null || fields.size()== 0 ){
            log.debug("未传入字段，默认选择查询所有字段！");
            fields = new ArrayList<>();
            fields.add("*");
        }
    }

    /**
     * 添加返回结果的字段   类似于(select field,field,field )
     * @param field 要添加的字段
     * @return SelectBaseExample对象
     */
    public SelectBaseExample addField(String field){
        if(this.fields == null){
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
        return this;
    }

    /**
     * 添加返回结果的字段   类似于(select field,field,field )
     * @param fields 要添加的字段list集合
     * @return SelectBaseExample对象
     */
    public SelectBaseExample addField(List fields){
        if(this.fields == null){
            this.fields = new ArrayList<>();
        }
        this.fields.addAll(fields);
        return this;
    }


    /**
     * 对SQL语句添加排序
     * @param field 要排序的字段名
     * @param orderType 使用ExampleConstants这个常量类
     * @return SelectBaseExample对象
     */
    public SelectBaseExample orderBy(String field,String orderType){
        if(this.order == null) {
            this.order = new HashMap<>();
        }
        this.order.put(field,orderType);
        return this;
    }

    /**
     * 对SQL语句添加分组
     * @param field 要分组的字段名
     * @return SelectBaseExample 对象
     */
    public SelectBaseExample groupBy(String field){
        this.groupBy = field;
        return this;
    }

    /**
     * 使用left join功能
     * @param table  表名
     */
    public void leftJoin(String table){

    }

    /**
     * 使用left join功能
     * @param table 表名
     * @param alias 别名
     */
    public void leftJoin(String table,String alias){

    }




    public Map<String, Object> getOrder() {
		return order;
	}

	public void setOrder(Map<String, Object> order) {
		this.order = order;
	}

	public String getGroupBy() {
        return groupBy;
    }
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
    public List<String> getFields() {
        return fields;
    }
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "SelectExample{" +
                ", orderBy=" + order +
                ", groupBy=" + groupBy +
                ", fields=" + fields +
                '}';
    }
}

