package com.mybatis.pj.util;

public class MyExampleSqlHelp {
	
	/**
	 * 查询指定列和获取表名
	 * @return 对应sql片段
	 */
	public static String getColumn() {
		return  "<foreach collection=\"_parameter.fields\" index=\"index\" item=\"item\" separator=\",\">\r\n" + 
				"		${item}\r\n" + 
				"	</foreach> from  ${tableName[0]}";
	}
	
	
	/**
	 * 查询指定更新列和获取表名
	 * @return 对应sql片段
	 */
	public static String getUpdateColumn() {
		return  "${tableName[0]} SET\r\n" + 
				"		<if test=\"@com.mybatis.pj.util.ExampleOGNL@useUpdateField(_parameter)\">\r\n" + 
				"			<foreach collection=\"updateFields\" item=\"key\" index=\"index\" separator=\",\">\r\n" + 
				"				${key} = #{updateValue[${index}]}\r\n" + 
				"			</foreach>\r\n" + 
				"		</if>";
	}
	
	
	/**
	 * 是否使用别名,有的话则获取第一个别名
	 * @return 对应sql片段
	 */
	public static String isUseAlias() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useAlias(_parameter) \">\r\n" + 
				"			${tableAlias[0]}\r\n" + 
				"		</if>";
	}
	
	/**
	 * 是否使用了左连接查询
	 * * @return 对应sql片段
	 */
	public static String isUseLeftJoin() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useMultipartAndLeftJoin(_parameter)\">\r\n" + 
				"			LEFT JOIN\r\n" + 
				"			<foreach collection=\"tableAlias\" item=\"key\" index=\"index\" separator=\"\">\r\n" + 
				"				<if test=\"index >= 1\">\r\n" + 
				"					${tableName[1]} ${key}\r\n" + 
				"				</if>\r\n" + 
				"			</foreach>\r\n" + 
				"			ON ${leftJoinOn}\r\n" + 
				"		</if>";
	}
	
	/**
	 * 是否使用了查询和是否使用了等号查询
	* @return 对应sql片段
	 */
	public static String useWhereAndEqualsWhere() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useWhere(_parameter)\">\r\n" + 
				"			<where>\r\n" + 
				"				<if test=\"@com.mybatis.pj.util.ExampleOGNL@useEqualsWhere(_parameter)\">\r\n" + 
				"					<foreach collection=\"equalsWhereKey\" item=\"key\" index=\"index\" separator=\"AND\"> " + 
				"						${key} = #{equalsWhereValue[${index}]} " + 
				"					</foreach>\r\n" + 
				"\r\n" + 
				"					<if test=\"@com.mybatis.pj.util.ExampleOGNL@useGreaterAndLess(_parameter)\">\r\n" + 
				"						AND\r\n" + 
				"					</if>\r\n" + 
				"				</if>"; 
	}
	
	/**
	 * 是否使用了大于查询
	 * @return 对应sql片段
	 */
	public static String useGreaterThan() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useGreaterThanWhere(_parameter)\">\r\n" + 
				"					<foreach collection=\"greaterThanWhereKey\" item=\"key\" index=\"index\" separator=\"AND\"> " + 
				"						${key} > #{greaterThanWhereValue[${index}]} " + 
				"					</foreach>\r\n" + 
				"					<if test=\"@com.mybatis.pj.util.ExampleOGNL@useLessAndNotEquals(_parameter)\">\r\n" + 
				"						AND\r\n" + 
				"					</if>\r\n" + 
				"				</if>"; 
	}
	
	/**
	 * 是否使用了小于查询
	 * @return 对应sql片段
	 */
	public static String useLessThan() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useLessThanWhere(_parameter)\">\r\n" + 
				"					<foreach collection=\"lessThanWhereKey\" item=\"key\" index=\"index\" separator=\"AND\"> " + 
				"						${key} &lt; #{lessThanWhereValue[${index}]} " + 
				"					</foreach>\r\n" + 
				"					<if test=\"@com.mybatis.pj.util.ExampleOGNL@useNotEqualsWhere(_parameter)\">\r\n" + 
				"						AND\r\n" + 
				"					</if>\r\n" + 
				"				</if>";
	}
	
	/**
	 * 是否使用了不等于查询
	 * @return 对应sql片段
	 */
	public static String useNotEquals() {
		return "<if test =\"@com.mybatis.pj.util.ExampleOGNL@useNotEqualsWhere(_parameter)\">\r\n" + 
				"					<foreach collection=\"notEqualsWhereKey\" item=\"key\" index=\"index\" separator=\"AND\"> " + 
				"						${key} != #{notEqualsWhereValue[${index}]}  " + 
				"					</foreach>\r\n" + 
				"				</if>\r\n" + 
				"			</where>\r\n" + 
				"		</if>";
	}
	
	/**
	 * 判断是否使用了查询
	 * @return 对应sql片段
	 */
	public static String notUseWhere() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@notUseWhere(_parameter)\">\r\n" + 
				"			WHERE 1 = 1\r\n" + 
				"		</if>";
	}
	
	/**
	 * 是否使用in查询
	 * @return
	 */
	public static String useIn() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useIn(_parameter)\">\r\n" + 
				"			AND\r\n" + 
				"			<foreach collection=\"inFields\" item=\"field\" index=\"index\" separator=\"AND\">\r\n" + 
				"				${field} in\r\n" + 
				"				<choose>\r\n" + 
				"					<when test=\"index == 0\">\r\n" + 
				"						<foreach collection=\"inValue1\" item=\"value1\" index=\"index1\" separator=\",\" open=\"(\" close=\")\">\r\n" + 
				"							#{value1}\r\n" + 
				"						</foreach>\r\n" + 
				"					</when>\r\n" + 
				"					<otherwise>\r\n" + 
				"						<foreach collection=\"inValue2\" item=\"value2\" index=\"index2\" separator=\",\" open=\"(\" close=\")\">\r\n" + 
				"							#{value2}\r\n" + 
				"						</foreach>\r\n" + 
				"					</otherwise>\r\n" + 
				"				</choose>\r\n" + 
				"			</foreach>\r\n" + 
				"		</if>";
	}
	
	/**
	 * 是否使用like查询
	 * @return
	 */
	public static String useLike() {
		return "<if test=\" @com.mybatis.pj.util.ExampleOGNL@useLike(_parameter)\">\r\n" + 
				"			<if test=\"@com.mybatis.pj.util.ExampleOGNL@useIn(_parameter)\">\r\n" + 
				"				AND\r\n" + 
				"			</if>\r\n" + 
				"			<foreach collection=\"likeFields\" item=\"field\" index=\"index\" separator=\"AND\">\r\n" + 
				"				${field} LIKE #{likeValues[${index}]}\r\n" + 
				"			</foreach>\r\n" + 
				"		</if>";
	}
	
	/**
	 * 是否使用group by
	 * @return 对应sql片段
	 */
	public static String useGroup() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useGroupBy(_parameter) \">\r\n" + 
				"			GROUP BY ${groupBy}\r\n" + 
				"		</if>";
	}
	
	/**
	 * 是否使用 order by
	 * @return 对应sql片段
	 */
	public static String useOrder() {
		return "<if test=\"@com.mybatis.pj.util.ExampleOGNL@useOrderBy(_parameter)\">\r\n" + 
				"			order by \r\n" + 
				"			<foreach collection=\"order.keys\" item=\"key\" index=\"index\" separator=\",\">\r\n" + 
				"				${key} " + 
				"				<choose>\r\n" + 
				"					<when test=\"@com.mybatis.pj.util.ExampleOGNL@orderByDesc(key,_parameter)\">\r\n" + 
				"						DESC \r\n" + 
				"					</when>\r\n" + 
				"					<otherwise>\r\n" + 
				"						ASC\r\n" + 
				"					</otherwise>\r\n" + 
				"				</choose>\r\n" + 
				"			</foreach>\r\n" + 
				"		</if>";
	}
}
