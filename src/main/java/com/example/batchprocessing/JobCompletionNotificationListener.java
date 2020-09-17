package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		log.info("DB登録処理開始します！");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("DB登録完了しました！結果を確認してください！");

			jdbcTemplate.query("SELECT product_code, name, kana FROM products",
				(rs, row) -> new Product(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3), null, row, null, row)
			).forEach(product -> log.info("商品登録データ>>>>>>>>>" + product));
		}
	}
}