package com.example.batchprocessing;

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

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Product> reader() {
		return new FlatFileItemReaderBuilder<Product>()
				.name("productItemREader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names(new String[] {"product_code", "name", "kana"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
	                setTargetType(Product.class);
	            }})
	            .build();
    }

    @Bean
    public ProductItemProcessor processor() {
    	return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product>writer(DataSource dataSource) {
    	return new JdbcBatchItemWriterBuilder<Product>()
    			.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO products (product_code, name, kana, created_at, created_user_id, updated_at, updated_user_id) "
                		+ "VALUES (:product_code, :name, :kana, :created_at, :created_user_id, :updated_at, :updated_user_id)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Product> writer) {
        return stepBuilderFactory.get("step1")
            .<Product, Product> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }



}
