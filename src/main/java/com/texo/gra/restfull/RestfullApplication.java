package com.texo.gra.restfull;

import com.texo.gra.restfull.model.Movie;
import com.texo.gra.restfull.utility.reader.MovieItemProcessor;
import com.texo.gra.restfull.utility.reader.MovieItemReader;
import com.texo.gra.restfull.utility.writer.MovieItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class RestfullApplication {

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;

	@Autowired
	public RestfullApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestfullApplication.class, args);
	}

	@Bean("movieStep")
	public Step buildMovieStep(MovieItemReader itemReader, MovieItemProcessor itemProcessor, MovieItemWriter itemWriter) {
		return stepBuilderFactory.get("movieStep")
				.<Movie, Movie>chunk(10)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
	}

	@Bean("movieJob")
	public Job buildMovieJob(final Step movieStep) {
		return jobBuilderFactory.get("movieLoadingJob")
				.incrementer(new RunIdIncrementer())
				.flow(movieStep)
				.end()
				.build();
	}

}
