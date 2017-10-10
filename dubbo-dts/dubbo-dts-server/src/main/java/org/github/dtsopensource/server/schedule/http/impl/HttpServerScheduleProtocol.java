package org.github.dtsopensource.server.schedule.http.impl;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.github.dtsopensource.schedule.LocalDTSSchedule;
import org.github.dtsopensource.server.schedule.http.IHttpServerSchedule;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.schedule.IDTSSchedule;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author ligaofeng 2016年12月14日 下午2:44:58
 */
public class HttpServerScheduleProtocol implements IHttpServerSchedule {
	private static final Logger logger = LoggerFactory.getLogger(HttpServerScheduleProtocol.class);
	
	@Resource
	private DataSource dataSource;

	@Resource
	private Map<String, IDTSTaskTracker> taskTrackerMap;

	private IDTSSchedule localDTSSchedule;

	/**
	 * initDTSSchedule
	 */
	@PostConstruct
	public void initDTSSchedule() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		localDTSSchedule = new LocalDTSSchedule(jdbcTemplate, transactionTemplate);
	}

	@Override
	public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException {
		logger.info("--->dts-server success recevie:{}", taskTrackerContext);
		String taskTrackerType = taskTrackerContext.getTaskTrackerType();
		IDTSTaskTracker taskTracker = taskTrackerMap.get(taskTrackerType);
		if (taskTracker == null) {
			throw new DTSBizException("尚不支持该任务:" + taskTrackerType);
		}
		taskTrackerContext.setDtsSchedule(localDTSSchedule);
		taskTracker.executeTask(taskTrackerContext);
	}

}
