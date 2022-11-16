package com.totalplay.app.Batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class TotalPlayProcessingBatchApplication{
	
	private static final Logger LOG = LoggerFactory.getLogger(TotalPlayProcessingBatchApplication.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {
		SpringApplication.run(TotalPlayProcessingBatchApplication.class, args);
	}
	
	//@Override
	public void run(String... args) throws Exception {
			LOG.info("INICIANDO EJECUCION..");
			long start = System.currentTimeMillis();
			
			BufferedReader reader = new BufferedReader(new FileReader(new File("example_10.csv")));
			String row = null;
			
			while ((row =  reader.readLine()) != null) {
				Object[] data = row.split(",");
				jdbcTemplate.update("INSERT INTO registro (nombre,apeido,direccion,telefono) VALUES(?, ?, ?, ?)",data);
			}	
			long total = jdbcTemplate.queryForObject("SELECT count(1) FROM registro", Long.class);
			long end = System.currentTimeMillis();
			long diff = end -start;
			reader.close();
			LOG.info("Tiempo de ejecucion : {}", TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));
			LOG.info("Registro: {}", total);}
}