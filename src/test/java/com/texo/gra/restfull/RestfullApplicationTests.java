package com.texo.gra.restfull;

import com.texo.gra.restfull.dto.ProducerDto;
import com.texo.gra.restfull.dto.ProducerYearsWinDto;
import com.texo.gra.restfull.model.Movie;
import com.texo.gra.restfull.repository.MovieRepository;
import com.texo.gra.restfull.service.ProducerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class RestfullApplicationTests {

	private static final String MOVIE_TITLE = "Rambo: Last Blood";
	private static final String MOVIE_PRODUCERS = "Avi Lerner, Kevin King Templeton, Yariv Lerner, and Les Weldon";
	private static final String MOVIE_STUDIOS = "Lionsgate";

	private final MovieRepository repository;
	private final ProducerService service;

	@Autowired
	RestfullApplicationTests(MovieRepository repository, ProducerService service) {
		this.repository = repository;
		this.service = service;
	}

	private static final List<Movie> movies = new ArrayList<>();
	private static Movie movie;

	@BeforeAll
	public static void start() {
		movie = new Movie();
		movie.setTitle(MOVIE_TITLE);
		movie.setProducers(MOVIE_PRODUCERS);
		movie.setStudios(MOVIE_STUDIOS);
		movie.setMovieYear(2022);
		movie.setWinner(true);
		movies.add(movie);
	}

	@Test
	public void createMovie() {
		List<Movie> moviesSaved = repository.saveAll(movies);
		if (moviesSaved.stream().findFirst().isPresent()) {
			movie = moviesSaved.stream().findFirst().get();
		}
		assertNotEquals(movie.getId(), null);
		assertEquals(movies.size(), moviesSaved.size());
	}

	@Test
	public void checkSavedMovieById() {
		Movie movieSave = new Movie();
		Optional<Movie> movieSaveOptional = repository.findById(movie.getId());
		if (movieSaveOptional.isPresent()) {
			movieSave = movieSaveOptional.get();
		}
		assertEquals(movieSave.getTitle(), MOVIE_TITLE);
	}

	@Test
	public void deleteMovieById() {
		repository.deleteById(movie.getId());
		Optional<Movie> movieSave = repository.findById(movie.getId());
		assertFalse(movieSave.isPresent());
	}

	@Test
	public void testSearchIntervalYears() {
		Set<Integer> year = new HashSet<>();
		year.add(2000);
		year.add(2014);
		year.add(2020);
		year.add(2012);
		year.add(1990);
		year.add(2002);
		year.add(2022);
		Set<ProducerDto> producerMin = service.searchIntervalYears(year, false);
		Set<ProducerDto> producerMax = service.searchIntervalYears(year, true);
		assertEquals(2, producerMin.stream().findFirst().orElse(new ProducerDto()).getInterval());
		assertEquals(3, producerMin.size());
		assertEquals(10, producerMax.stream().findFirst().orElse(new ProducerDto()).getInterval());
		assertEquals(2, producerMax.size());
	}

	@Test
	public void testIsValidIntervalMin() {
		assertTrue(service.isValidInterval(8, 10, false));
	}

	@Test
	public void testIsValidIntervalMax() {
		assertTrue(service.isValidInterval(10, 5, true));
	}

}
