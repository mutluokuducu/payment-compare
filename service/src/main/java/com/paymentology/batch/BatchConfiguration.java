package com.paymentology.batch;

import com.paymentology.dto.TransactionDto;
import com.paymentology.exeption.ErrorType;
import com.paymentology.exeption.TransactionServiceException;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Autowired
  public DataSource dataSource;
  @Autowired
  private JobBuilderFactory jobBuilders;
  @Autowired
  private StepBuilderFactory stepBuilders;

  @Bean
  public Job jobProcess(NotificationListener notificationListener, Step step) {
    try {
      return jobBuilders.get("jobProcess")
          .listener(notificationListener)
          .start(step)
          .build();
    } catch (IllegalStateException | FlatFileParseException ex) {
      throw new TransactionServiceException(ErrorType.INTERNAL_ERROR);
    }
  }

  @Bean
  public Step step(ItemReader<TransactionDto> reader) {
    return stepBuilders.get("step")
        .<TransactionDto, TransactionDto>chunk(5)
        .reader(reader)
        .processor(processor())
        .writer(writer())
        .faultTolerant()
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<TransactionDto> reader(
      @Value("#{jobParameters[pathToFile]}") String pathToFile) {
    try {
      return new FlatFileItemReaderBuilder<TransactionDto>()
          .linesToSkip(1)
          .name("personItemReader")
          .resource(new ClassPathResource(pathToFile))
          .delimited()
          .names(new String[]{
              "ProfileName", "TransactionDate", "TransactionAmount",
              "TransactionNarrative", "TransactionDescription", "TransactionId", "TransactionType",
              "WalletReference"})
          .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
            setTargetType(TransactionDto.class);
          }})
          .build();
    } catch (IllegalStateException | FlatFileParseException ex) {
      throw new TransactionServiceException(ErrorType.INTERNAL_ERROR);

    }
  }

  @Bean
  public TransactionItemProcessor processor() {
    return new TransactionItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<TransactionDto> writer() {
    return new JdbcBatchItemWriterBuilder<TransactionDto>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .dataSource(dataSource)
        .beanMapped()
        .sql(
            "INSERT INTO transaction_csv(file_name,profile_name,transaction_date,transaction_amount,transaction_narrative,transaction_description,transaction_id,transaction_type,wallet_reference) "
                + "VALUES (:fileName,:profileName,:transactionDate,:transactionAmount,:transactionNarrative,:transactionDescription,:transactionId,:transactionType,:walletReference)")

        .build();
  }
}

