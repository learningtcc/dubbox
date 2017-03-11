package com.alibaba.dubbo.sqlhint.masterslave;

/**
 * Mycat 数据库中间件的SQL hint 实现
 * 
 * @author Ternence
 * @date 2016年7月20日
 */
public class MycatSqlHint extends MasterSlaveHint {

	private static final String MYCAT_DB_MASTER_HINT = "/*#mycat:db_type=master*/";

	private static final String MYCAT_DB_SLAVE_HINT = "/*#mycat:db_type=slave*/";

	@Override
	public String getRouteMasterHint() {
		return MYCAT_DB_MASTER_HINT;
	}

	@Override
	public String getRouteSlaveHint() {
		return MYCAT_DB_SLAVE_HINT;
	}

	@Override
	public String genRouteInfo(String dbRole, String sql) {
		if (MASTER.equals(dbRole)) {
			sql = new StringBuilder(getRouteSlaveHint()).append(sql).toString(); // master
																					// only
		} else {

		}

		return sql;
	}

}
