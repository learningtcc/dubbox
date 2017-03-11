package com.alibaba.dubbo.sqlhint.masterslave;

/**
 * Altas 数据库中间件的SQL hint 实现
 * 
 * @author Ternence
 * @date 2016年7月20日
 */
public class AtlasSqlHint extends MasterSlaveHint {

	private static final String DB_MASTER_HINT = "/*master*/";

	private static final String DB_SLAVE_HINT = "/*slave*/";

	@Override
	public String getRouteMasterHint() {
		return DB_MASTER_HINT;
	}

	@Override
	public String getRouteSlaveHint() {
		return DB_SLAVE_HINT;
	}

	@Override
	public String genRouteInfo(String dbRole, String sql) {
		return new StringBuilder(getRouteSlaveHint()).append(sql).toString();
	}

}
