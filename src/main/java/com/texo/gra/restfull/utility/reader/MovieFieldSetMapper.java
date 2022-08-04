package com.texo.gra.restfull.utility.reader;

import com.texo.gra.restfull.model.Movie;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class MovieFieldSetMapper implements FieldSetMapper<Movie> {

    @Override
    public Movie mapFieldSet(FieldSet fieldSet) {
        Movie movie = new Movie();
        movie.setMovieYear(fieldSet.readInt(0));
        movie.setTitle(fieldSet.readString(1));
        movie.setStudios(fieldSet.readString(2));
        movie.setProducers(fieldSet.readString(3));
        movie.setWinner(fieldSet.readString(4).contains("yes"));
        return movie;
    }
}
