package com.texo.gra.restfull.utility.writer;

import com.texo.gra.restfull.model.Movie;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MovieItemWriter extends JdbcBatchItemWriter<Movie> {

    private static final String SQL_COMMAND =
            "INSERT INTO TB_MOVIES (MOVIE_YEAR, PRODUCERS, STUDIOS, TITLE, WINNER) " +
                    "VALUES (:movieYear, :producers, :studios, :title, :winner) ";

    public MovieItemWriter(DataSource dataSource) {
        this.setDataSource(dataSource);
        this.setSql(SQL_COMMAND);
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.afterPropertiesSet();
    }

}
