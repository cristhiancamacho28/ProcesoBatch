package com.totalplay.app.Batch;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.totalplay.app.Batch.listener.JobListener;
import com.totalplay.app.Batch.models.Registro;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration{
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public FlatFileItemReader<Registro> reader(){
		return new FlatFileItemReaderBuilder<Registro>()
				.name("registroItemReader")
				.resource(new ClassPathResource("example_10.csv"))
				.delimited()
				.names(new String [] {"nombre", "apeido", "direccion", "telefono"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Registro>(){{
					setTargetType(Registro.class);}})
				.linesToSkip(1)
				.build();}
	@Bean
	public JdbcBatchItemWriter<Registro> writter(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Registro>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Registro>())
						.sql("INSERT INTO registro (id_registro, nombre, apeido, direccion, telefono)VALUES(:SEQUENCE1.NEXTVAL, :nombre, :apeido, :direccion, :telefono)")
				.dataSource(dataSource)
				.build();}
	@Bean
	public Job importRegistroJob(JobListener listener, Step step1) {
		return jobBuilderFactory.get("importRegistroJob") 
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();}
	@Bean
	public Step step1(JdbcBatchItemWriter<Registro> writer) {
		return stepBuilderFactory.get("step1")
				.<Registro, Registro>chunk(10)
				.reader(reader())
				.writer(writer)
				.build();}
}
