package com.mybatis.pj.example.util;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 反射的Utils函数集合.扩展自Apache Commons BeanUtils
 */
@SuppressWarnings({"unchecked","rawtypes","unused"})
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    /**
     * 通过set方法赋值
     * @param object
     * @param propertyName
     * @param newValue
     */
    public static void forceSetProperty(Object object, String propertyName, Object newValue) {
        if(null==object|| StringHelper.isBlank(propertyName))return;
        String[] s=propertyName.split("\\.");
        if(null==s)return;
        for (int i = 0; i < s.length-1; i++) {
            object=forceGetProperty(object, s[i]);
        }
        try {
            if(object instanceof Map) {
                ((Map)object).put(propertyName, newValue);
            } else {
                setObjValue(object,propertyName,newValue);
            }
        } catch (Exception e) {
        }
    }

    public static Object forceGetProperty(Object object, String propertyName) {
        Object result=null;
        try {
            if(object instanceof Map) {
                result = ((Map)object).get(propertyName);
            } else {
                result = getObjValue(object, propertyName, null);
            }
        } catch (Exception e) {
        }
        return result;
    }
    /**
     * 获取对象里对应的属性值(通过get方法)
     * @param obj
     * @param name
     * @param defObj 默认值
     * @return
     */
    public static Object getObjValue(Object obj,String name,Object defObj){
        Object valueObj=null;
        String methodName=getProNameMethod(name);
        Method method=transferMethoder(obj, methodName, new Class[0]);
        if(null!=method){
            try {
                valueObj=method.invoke(obj);
                if(null==valueObj){
                    valueObj=defObj;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueObj;
    }
    /**
     * 获取对应的名称的get方法
     * @param proName
     * @return
     */
    public static String getProNameMethod(String proName){
        String methodName="";
        if(StringHelper.isNotBlank(proName)){
            methodName="get"+getFirstUpper(proName);
        }
        return methodName;
    }

    /**
     * 通过反射动态调用方法
     * @param obj 要调用的类
     * @param methodname 方法名称
     * @param types 调用方法需要的参数类型数组（顺序排列）
     * @return
     */
    public static Method transferMethoder(Object obj,String methodname,Class types[]) {
        try {
            Class clazz = obj.getClass();
            for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
                return superClass.getMethod(methodname,types);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    /**
     * 赋值对象里对应的属性值(通过set方法)
     * @param obj
     * @param name
     * @param defObj 值
     * @return
     */
    public static void setObjValue(Object obj,String name,Object defObj){
        String methodName=getProSetNameMethod(name);
        try {
            Field field = getDeclaredField(obj.getClass(), name);
            Class fclass=field.getType();
            Object valueobj=getValueByType(fclass.getName(), defObj);
            Class[] types={fclass};
            Method method=transferMethoder(obj, methodName, types);
            if(null!=method){
                method.invoke(obj,valueobj);
            }
        } catch (Exception e) {
        }
    }
    /**
     * 获取对应的名称的set方法
     * @param proName
     * @return
     */
    public static String getProSetNameMethod(String proName){
        String methodName="";
        if(StringHelper.isNotBlank(proName)){
            methodName="set"+getFirstUpper(proName);
        }
        return methodName;
    }


    private static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {

            }
        }
        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
    }

    /**
     * @param className
     * @param defObj
     * @return
     */
    public static Object getValueByType(String className,Object defObj){
        Object obj=null;
        if(className.indexOf("String")>=0){
            if(null==defObj){
                obj=null;
            }else{
                obj=defObj+"";
            }
        }else if(className.indexOf("int")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="0";
            }
            obj=Long.valueOf(String.valueOf(defObj)).intValue();
        }else if(className.indexOf("Long")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="0";
            }
            obj=Long.valueOf(String.valueOf(defObj));
        }else if(className.indexOf("Double")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="0";
            }
            obj=Double.valueOf(String.valueOf(defObj));
        }else if(className.indexOf("double")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="0";
            }
            obj=Double.valueOf(String.valueOf(defObj));
        }else if(className.indexOf("Date")>=0){
            if(null!=defObj&&StringHelper.isNotBlank(String.valueOf(defObj))){
                if(String.valueOf(defObj).length()>10){
                    obj=DateUtil.getDateToString(String.valueOf(defObj), DateUtil.DATETIMESHOWFORMAT);
                }else{
                    obj=DateUtil.getDateToString(String.valueOf(defObj), DateUtil.DATESHOWFORMAT);
                }
                if(obj == null){
                    obj = defObj;
                }
            }
        }else if(className.indexOf("Integer")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="0";
            }
            obj=Integer.valueOf(String.valueOf(defObj));
        }else if(className.indexOf("boolean")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="false";
            }
            if("true".equals(String.valueOf(defObj))){
                obj=true;
            }else{
                obj=false;
            }
        }else if(className.indexOf("Boolean")>=0){
            if(StringHelper.isBlank(String.valueOf(defObj))){
                defObj="false";
            }
            if("true".equals(String.valueOf(defObj))){
                obj=true;
            }else{
                obj=false;
            }
        }
        return obj;
    }




    /**
     * 把字符串第一个字母转成大写
     * @param str
     * @return
     */
    private static String getFirstUpper(String str){
        String newStr="";
        if(str.length()>0){
            newStr=str.substring(0, 1).toUpperCase()+str.substring(1, str.length());
        }
        return newStr;
    }

    /**
     * 把orig对象的同名非空属性复制给dest对象
     * @param dest
     * @param orig
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public static void copyPropertiesNotNull(Object dest, Object orig)
        throws IllegalAccessException, InvocationTargetException {
		PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] =
                ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (propertyUtilsBean.isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (value != null) {
                    	copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if (propertyUtilsBean.isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    if (value != null) {
                    	copyProperty(dest, name, value);
                    }
                }
            }
        } else {
            PropertyDescriptor origDescriptors[] =
                propertyUtilsBean.getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (propertyUtilsBean.isReadable(orig, name) &&
                    propertyUtilsBean.isWriteable(dest, name)) {
                    try {
                        Object value =
                            propertyUtilsBean.getSimpleProperty(orig, name);
                        if (value != null) {
                        	copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        ; // Should not happen
                    }
                }
            }
        }
    }
}
