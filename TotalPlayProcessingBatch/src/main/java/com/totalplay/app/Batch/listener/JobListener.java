package com.totalplay.app.Batch.listener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobListener extends JobExecutionListenerSupport{
	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus()==BatchStatus.COMPLETED) {
			LOG.info("Registro job with id{} execution completed", jobExecution.getId());
			LOG.info("FINALIZO EL JOB!! VERIFICA LOS RESULTADOS: ");
			Date start = jobExecution.getStartTime();
			Date end = jobExecution.getEndTime();
			long diff = end.getTime() - start.getTime();
			long total = jdbcTemplate.queryForObject("SELECT count(1) FROM registro", Long.class);
			LOG.info("Tiempo de ejecucion : {}", TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));
			LOG.info("Registro: {}", total);	
		}
	}
}
/*@Component
public class JobListener implements JobExecutionListener {
	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOG.info("Executing cars job with id {}", jobExecution.getId());
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOG.info("Cars job with id {} execution completed", jobExecution.getId());
        }
    }
}*/


          