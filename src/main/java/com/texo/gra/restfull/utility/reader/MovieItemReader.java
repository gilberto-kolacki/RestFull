package com.texo.gra.restfull.utility.reader;

import com.texo.gra.restfull.model.Movie;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class MovieItemReader extends FlatFileItemReader<Movie> {

    public MovieItemReader() {
        DefaultLineMapper<Movie> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer(";"));
        defaultLineMapper.setFieldSetMapper(new MovieFieldSetMapper());
        this.setLineMapper(defaultLineMapper);
        this.setEncoding("UTF-8");
        this.setComments(new String[]{"#", "year"});
        this.setResource(new ClassPathResource("movielist.csv"));
    }

}
