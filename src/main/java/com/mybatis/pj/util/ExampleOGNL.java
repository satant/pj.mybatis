package com.mybatis.pj.util;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis.pj.constant.ExampleConstants;
import com.mybatis.pj.example.BaseExample;
import com.mybatis.pj.example.select.SelectBaseExample;
import com.mybatis.pj.example.select.multipart.MultipartSelectExample;
import com.mybatis.pj.example.update.UpdateBaseExample;

public class ExampleOGNL {
	static Logger logger = LoggerFactory.getLogger(ExampleOGNL.class);
	
	/**
	 * 判断是否含有更新字段
	 * @return
	 */
	public static boolean useUpdateField(Object parameter) {
		if(parameter != null && parameter instanceof UpdateBaseExample) {
			UpdateBaseExample ube = (UpdateBaseExample)parameter;
			List<String> updateFields = ube.getUpdateFields();
			if(updateFields != null && updateFields.size() !=0) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否使用多功能查询和左连接查询
	 * @param parameter 查询参数
	 */
	public static boolean useMultipartAndLeftJoin(Object parameter) {
		if(parameter != null && parameter instanceof MultipartSelectExample) {
			MultipartSelectExample mse = (MultipartSelectExample) parameter;
			if(mse.isLeftJoinFlag()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否使用别名
	 * @param parameter 查询参数
	 */
	public static boolean useAlias(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> tableAlias = sbe.getTableAlias();
			if(tableAlias != null && tableAlias.size()>0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 使用了where查询
	 * @param parameter 查询参数
	 */
	public static boolean useWhere(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			if(sbe.getEqualsWhereKey() != null || sbe.getGreaterThanWhereKey() != null || sbe.getLessThanWhereKey() != null || sbe.getNotEqualsWhereKey() != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 没有使用where查询
	 * @param parameter 查询参数
	 */
	public static boolean notUseWhere(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			if(sbe.getEqualsWhereKey() == null && sbe.getGreaterThanWhereKey() == null && sbe.getLessThanWhereKey() == null && sbe.getNotEqualsWhereKey() == null) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 是否使用了大于，小于或者不等于查询
	 * @param parameter 查询参数
	 */
	public static boolean useGreaterAndLess(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> greaterThan = sbe.getGreaterThanWhereKey();
			List<String> lessThanWhere = sbe.getLessThanWhereKey();
			List<String> notEqualsWhere = sbe.getNotEqualsWhereKey();
			if((greaterThan != null &&greaterThan.size() >0) ||(lessThanWhere != null &&lessThanWhere.size() >0) || (notEqualsWhere != null && notEqualsWhere.size()>0)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用了小于或者不等于查询
	 * @param parameter 查询参数
	 */
	public static boolean useLessAndNotEquals(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> lessThanWhere = sbe.getLessThanWhereKey();
			List<String> notEqualsWhere = sbe.getNotEqualsWhereKey();
			if((lessThanWhere != null &&lessThanWhere.size() >0) || (notEqualsWhere != null && notEqualsWhere.size()>0)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用了等号查询
	 * @param parameter 查询参数
	 */
	public static boolean useEqualsWhere(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> equalsWhere = sbe.getEqualsWhereKey();
			if(equalsWhere != null && equalsWhere.size() >0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用了大于查询
	 * @param parameter 查询参数
	 */
	public static boolean useGreaterThanWhere(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> greaterThan = sbe.getGreaterThanWhereKey();
			if((greaterThan != null &&greaterThan.size() >0)  ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用了小于查询
	 * @param parameter 查询参数
	 */ 
	public static boolean useLessThanWhere(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> lessThanWhere = sbe.getLessThanWhereKey();
			if(lessThanWhere != null && lessThanWhere.size() >0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用了不等于查询
	 * @param parameter 查询参数
	 */ 
	public static boolean useNotEqualsWhere(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> notEqualsWhere = sbe.getNotEqualsWhereKey();
			if(notEqualsWhere != null && notEqualsWhere.size() >0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用了in查询
	 * @param parameter 查询参数
	 */
	public static boolean useIn(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> inFields = sbe.getInFields();
			if(inFields != null && inFields.size() >0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 使用了like查询
	 * @param parameter 查询参数
	 */
	public static boolean useLike(Object parameter) {
		if(parameter != null) {
			BaseExample sbe = (BaseExample)parameter;
			List<String> likeFields = sbe.getLikeFields();
			if(likeFields != null && likeFields.size() >0) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 是否使用了分组
	 * @param parameter 查询参数
	 */
	public static boolean useGroupBy(Object parameter) {
		if(parameter != null) {
			SelectBaseExample sbe = (SelectBaseExample)parameter;
			String groupByField = sbe.getGroupBy();
			if(groupByField !=null && groupByField.length()>0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用了排序
	 * @param parameter 查询参数
	 */
	public static boolean useOrderBy(Object parameter) {
		if(parameter != null) {
			SelectBaseExample sbe = (SelectBaseExample)parameter;
			Map<String, Object> orderBy = sbe.getOrder();
			if(orderBy != null && orderBy.size() >0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 是否是desc排序
	 * @param parameter 查询参数
	 */
	public static boolean orderByDesc(String key,Object parameter) {
		if(parameter != null) {
			SelectBaseExample sbe = (SelectBaseExample)parameter;
			Map<String, Object> orderBy = sbe.getOrder();
			if(ExampleConstants.ORDER_BY_DESC.equals(orderBy.get(key))) {
				return true;
			}
		}
		return false;
	}
	
}
