package com.texo.gra.restfull.utility.reader;

import com.texo.gra.restfull.model.Movie;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovieItemProcessor implements ItemProcessor<Movie, Movie> {
    @Override
    public Movie process(final Movie movie) {
        if (movie.getTitle() == null || movie.getMovieYear() == null) {
            return null;
        }
        return movie;
    }
}
