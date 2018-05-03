package com.mybatis.pj.example.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mybatis.pj.example.select.SelectBaseExample;
import com.mybatis.pj.example.select.multipart.MultipartSelectExample;

/**
 * Created with IntelliJ IDEA.
 * User: satant
 * Date: 2018/4/3
 * Time: 15:46
 * Description:
 */
public class BaseExampleUtil {
    static final Logger log = LoggerFactory.getLogger(BaseExampleUtil.class);


    /**
     * 将结果集(数据库字段)转化为类属性
     * @param list
     * @param example
     * @param clzs
     */
    public static void list2Property(List<Map<String,Object>> list, SelectBaseExample example, Class<?> ... clzs) {
        //要搜索的字段数组
        List<String> fields = example.getFields();
        //获取class类
        Field[] classFields =null;
        //字段的别名
        String alias;
        //数据库字段名
        String sqlField;
        Map<String,String> map = new HashMap<>();
        // 数据库字段对应->class.数据库字段
        Map<String,String> sqlField2classField = null;
        //获取属性与字段的映射
        //只有一张表，且不传字段或者传入的字段为*
        if (fields == null || fields.size() == 0 || "*".equals(fields.get(0))){
            if(example instanceof MultipartSelectExample){
                if(example.getTableMap() != null){
                    throw new IllegalArgumentException("在结果字段为空或者为*时，不允许定义别名！");
                }
            }
            if (example.getTableName().size() != 1) {
                throw new IllegalArgumentException("在结果字段为空或者为*时，不允许出现两个表名！");
            }
            classFields = clzs[0].getDeclaredFields();
            //开始进行属性的封装
            selectField2FieldMap(null,classFields,null,null,null,map,true,false);
        }else {
            //判断是否有别名
            if(example.getTableMap() != null){
                sqlField2classField = new HashMap<>();
                //获取的目标class
                Class targetClass = null;
                //别名与表名的映射
                Map<String,String> tableMap = example.getTableMap();
                //判断是否是多功能查询
                if(example instanceof MultipartSelectExample){
                    //判断是一张表还是使用了left join
                    if(((MultipartSelectExample) example).isLeftJoinFlag()){
                        //封装class和别名的映射
                        Map<String,Class> classMap = sqlTableName2Class(tableMap,clzs);
                        for(String key :fields) {
                            //key有两种形式，  一：a.*    二:a.felds
                            String[] split = key.split("\\.");
                            alias = split[0];
                            sqlField = split[1];
                            //判断是否有*
                            //是*的字段
                            targetClass = classMap.get(alias);
                            classFields = targetClass.getDeclaredFields();
                            if("*".equals(split[1])){
                                //获取属性field,并添加到map中
                                selectField2FieldMap(null,classFields,null,sqlField2classField,targetClass,map,true,true);
                            }else{
                                //不是*的字段
                                selectField2FieldMap(null,classFields,sqlField,sqlField2classField,targetClass,map,false,true);
                            }
                        }
                    }else{
                        //没有使用left join 则只有一张表，有别名
                        for(String key : fields) {
                            targetClass = clzs[0];
                            classFields = targetClass.getDeclaredFields();
                            //数据库字段key
                            String[] split = key.split("\\.");
                            sqlField = split[1];
                            //判断是否含有*
                            if("*".equals(sqlField)){
                                //含有*
                                selectField2FieldMap(null,classFields,sqlField,sqlField2classField,targetClass,map,true,true);
                            }else{
                                //不是*的字段
                                selectField2FieldMap(null,classFields,sqlField,sqlField2classField,targetClass,map,false,true);
                            }
                        }
                    }
                }else{
                    //基础查询，只有一张表，有别名
                    //这里需要判断一下是否是*
                    for(String key : fields) {
                        targetClass = clzs[0];
                        String[] split = key.split("\\.");
                        sqlField = split[1];
                        classFields = targetClass.getDeclaredFields();
                        //判断是否有*
                        if("*".equals(sqlField)){
                            //含有*
                            selectField2FieldMap(null,classFields,sqlField,sqlField2classField,targetClass,map,true,true);
                        }else{
                            //不含*
                            selectField2FieldMap(null,classFields,sqlField,sqlField2classField,targetClass,map,false,true);
                        }
                    }
                }
            }else{
                //基础查询，未使用别名，且只有一张表,不是*字段
                //数据库字段key
                Class<?> clz = clzs[0];
                classFields = clz.getDeclaredFields();
                selectField2FieldMap(fields,classFields,null,null,null,map,false,false);
            }
        }
        copyList(list,map,sqlField2classField);
    }


    /**
     * 属性替换
     * @param list 原结果集
     * @param map  字段映射  image.create_date  ->  createDate   如果没有别名则为   create_date  ->  createDate
     * @param middleMap 数据库字段与类字段的映射 a.create_date  ->  image.create_date     如果没有别名则为null
     */
    private static void copyList(List<Map<String,Object>> list,Map<String,String> map,Map<String,String> middleMap){
        int size = list.size();
        if(middleMap  == null){
            for(int i =0 ; i<size ;i++) {
                Map<String,Object> map2 = new HashMap<>();
                Map<String, Object> map1 = list.get(i);
                Set<Map.Entry<String, Object>> es = map1.entrySet();
                for ( Map.Entry<String,Object> entry : es) {
                    String oldKey = entry.getKey().toLowerCase();
                    Object value = entry.getValue();
                    String newKey = map.get(oldKey);
                    if(!StringUtils.isBlank(newKey)){
                        map2.put(newKey, value);
                    }else{
                        map2.put(oldKey, value);
                    }
                }
                list.remove(i);
                list.add(i,map2);
            }
        }else{
            for(int i =0 ; i<size ;i++) {
                Map<String,Object> map2 = new HashMap<>();
                Map<String, Object> map1 = list.get(i);
                Set<Map.Entry<String, Object>> es = map1.entrySet();
                for ( Map.Entry<String,Object> entry : es) {
                    String oldKey = entry.getKey().toLowerCase();
                    Object value = entry.getValue();
                    //中间字段
                    String classFiled = middleMap.get(oldKey);
                    if(!StringUtils.isBlank(classFiled)){
                        String newKey = map.get(classFiled);
                        map2.put(newKey, value);
                    }else{
                        map2.put(oldKey, value);
                    }
                }
                list.remove(i);
                list.add(i,map2);
            }
        }
    }

    /**
     * 类名转Class类
     * @param map map中应装有别名与表名的映射
     * @param clzs 要封装的类型
     * @return
     */
    private static Map<String,Class> sqlTableName2Class(Map<String,String> map , Class<?> ... clzs){
        Set<Map.Entry<String, String>> set = map.entrySet();
        Map<String,Class> classMap = new HashMap<>();
        set.forEach(entry -> {
            String tableName = entry.getValue();
            for(Class<?> clz : clzs){
                Table t = clz.getAnnotation(Table.class);
                if(tableName.equals(t.name())){
                    classMap.put(entry.getKey(),clz);
                }
            }
        });
        return classMap;
    }

    /**
     * 封装map    有两种情况       1：使用了别名，map中封装的为 类名.sql字段名  ->  类字段名
     *                             2：未使用别名，map中封装的为 sql中字段名  ->  类字段名
     * @param classFields   类的所有字段
     * @param sqlField   传入的sql字段名
     * @param sqlField2classField  sql字段与类字段的映射
     * @param targetClass  目标类
     * @param isAll  是否全部类型  true：是，即传入的字段有*   false：不是，即传入的是sql字段
     * @param useAlias  是否使用别名   true：使用了别名     false： 未使用别名
     * @param map   有两种情况       1：使用了别名，map中封装的为 类名.sql字段名  ->  类字段名
     *                                2：未使用别名，map中封装的为 sql中字段名  ->  类字段名
     */
    public static void selectField2FieldMap(List<String>fields , Field[] classFields,String sqlField,Map<String,String> sqlField2classField,Class targetClass,Map<String,String> map,boolean isAll,boolean useAlias){
        //是否查所有字段
        if(isAll){
            //查所有字段
            //是否含有别名
            if (useAlias) {
                //查所有字段，含有别名
                for (Field field : classFields) {
                    Column annotation = field.getAnnotation(Column.class);
                    String name = annotation.name().toLowerCase();
                    sqlField2classField.put(name, targetClass.getSimpleName() + "." + name);
                    map.put(targetClass.getSimpleName() + "." + name, field.getName());
                }
            }else{
                //查所有字段，不含别名
                for (Field field : classFields) {
                    Column annotation = field.getAnnotation(Column.class);
                    String name = annotation.name().toLowerCase();
                    //没有别名传这个时传null  sqlField2classField
                    map.put(name, field.getName());
                }
            }
        }else {
            //查指定字段
            if (useAlias) {
                //查指定字段，使用了别名
                for (Field field : classFields) {
                    Column annotation = field.getAnnotation(Column.class);
                    String name = annotation.name();
                    if (sqlField.toLowerCase().equals(name)) {
                        sqlField2classField.put(name, targetClass.getSimpleName() + "." + name);
                        map.put(targetClass.getSimpleName() + "." + sqlField.toLowerCase(), field.getName());
                        break;
                    }
                }
            } else {
                //查指定字段，未使用别名
                for( String key :fields) {
                    for (Field field : classFields) {
                        Column annotation = field.getAnnotation(Column.class);
                        String name = annotation.name().toLowerCase();
                        if (key.toLowerCase().equals(name)) {
                            //未使用别名，只有一张表，sqlField2classField也时null
                            map.put(key.toLowerCase(), field.getName());
                        }
                    }
                }
            }
        }
    }





    /**
     * 查询后的map转为实体对象
     * @param map map集合
     * @param clz 目标class类型
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public  static Object  map2Object(Map<String,Object> map, Class<?> clz)throws InstantiationException,IllegalAccessException{
        Object obj = null;
        try {
            obj = clz.newInstance();
        } catch (InstantiationException e) {
            log.error("创建对象时出现异常");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String,Object> entry:entries) {
            String field = entry.getKey();
            BeanUtils.forceSetProperty(obj,field,entry.getValue());
        }
        return obj;
    }


    /**
     * 搜索前将传入的Class字段名map转换为sql字段名map
     * @param param 类字段名与搜索值的集合
     * @param clz Class对象
     * @return
     */
    public static Query classFieldMap2SqlFieldMap(Map<String,Object> param, Class<?> clz){
        Field[] fields = clz.getDeclaredFields();
        Map<String,String> ObjectField2sqlField = new HashMap<String,String>();
        Map<String,Object> map = new HashMap<String,Object>();
        if(param.get("page")!=null) {
            map.put("page",param.get("page"));
            param.remove("page");
        }
        if(param.get("limit")!=null) {
            map.put("limit",param.get("limit"));
            param.remove("limit");
        }
        for (Field field:fields) {
            Column annotation = field.getAnnotation(Column.class);
            String sqlField = annotation.name();
            ObjectField2sqlField.put(field.getName(),sqlField);
        }
        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String sqlField = ObjectField2sqlField.get(entry.getKey());
            iterator.remove();
            map.put(sqlField,entry.getValue());
        }
        Query sqlQuery = new Query(map);
        return sqlQuery;
    }

    /**
     * 替换list中的Class字段为sql字段
     * @param fields class字段list
     * @param clz 目标类
     * @return
     */
    public static List<String> classFieldList2SqlFieldList(List<String> fields ,Class<?> clz){
        Field[] Classfields = clz.getDeclaredFields();
        Map<String,String> objectField2sqlField = new HashMap<String,String>();
        for (Field field:Classfields) {
            Column annotation = field.getAnnotation(Column.class);
            String sqlField = annotation.name();
            objectField2sqlField.put(field.getName(),sqlField);
        }
        int size = fields.size();
        fields.replaceAll(field -> {
            String sqlField = objectField2sqlField.get(field);
            return sqlField;
        });
        return fields;
    }


    /**
     * 将返回的map类型集合->转换为->对应对象集合
     * @param list map集合
     * @param clz 要转换成的目标类型
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public  static List<?>  list2Object(List<Map<String,Object>> list, Class<?> clz)throws InstantiationException,IllegalAccessException{
        List<Object> newList = new ArrayList<>();
        for(Map<String,Object> map :list){
            newList.add(map2Object(map,clz));
        }
        return newList;
    }
}