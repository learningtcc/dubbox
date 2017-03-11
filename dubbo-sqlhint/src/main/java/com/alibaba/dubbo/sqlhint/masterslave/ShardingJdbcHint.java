package com.alibaba.dubbo.sqlhint.masterslave;

import com.dangdang.ddframe.rdb.sharding.api.HintManager;

/**
 * sjdbc的 thread hint
 * 
 * @author Ternence
 * @date 2016年9月2日
 */
public class ShardingJdbcHint extends MasterSlaveHint {

	@Override
	public String getRouteMasterHint() {
		HintManager hintManager = HintManager.getInstance();
		hintManager.setMasterRouteOnly();
		return null;
	}

	@Override
	public String getRouteSlaveHint() {
		return null;
	}

	@Override
	public String genRouteInfo(String dbRole, String sql) {
		// 基于 thread的hint
		if (MASTER.equals(dbRole))
			getRouteMasterHint();
		return sql;
	}

}
