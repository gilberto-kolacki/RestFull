package com.texo.gra.restfull.service;

import com.texo.gra.restfull.dto.AwardRangeMinMaxDto;
import com.texo.gra.restfull.dto.ProducerDto;
import com.texo.gra.restfull.dto.ProducerYearsWinDto;
import com.texo.gra.restfull.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final MovieRepository movieRepository;

    @Autowired
    public ProducerService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public AwardRangeMinMaxDto getAwardRangeMinMax() {
        AwardRangeMinMaxDto awardRangeMinMaxDto = new AwardRangeMinMaxDto();
        Set<String> producersMovies = this.getProducersListName();
        Set<ProducerYearsWinDto> producersYearsWin = this.getProducerYearsWin(producersMovies);
        awardRangeMinMaxDto.setMin(this.searchIntervalAwards(producersYearsWin, false));
        awardRangeMinMaxDto.setMax(this.searchIntervalAwards(producersYearsWin, true));
        return awardRangeMinMaxDto;
    }

    private Set<ProducerYearsWinDto> getProducerYearsWin(Set<String> producersMovies) {
        Set<ProducerYearsWinDto> producersYearsWinDto = new HashSet<>();
        for (String producer : producersMovies) {
            Set<Integer> yearsWin = this.movieRepository.findYearsWinnersByProducer(producer);
            if (yearsWin.size() > 1) {
                producersYearsWinDto.add(new ProducerYearsWinDto(producer, yearsWin));
            }
        }
        return producersYearsWinDto;
    }

    private Set<String> getProducersListName() {
        Set<String> producersNames = new HashSet<>();
        Set<String> producersMovies = this.movieRepository.findWinnersProducers();
        for (String producersMovie : producersMovies) {
            producersNames.addAll(Arrays.stream(producersMovie.split(",")).filter(elem -> !elem.isEmpty()).collect(Collectors.toList())
            );
        }
        return producersNames;
    }

    private Set<ProducerDto> searchIntervalAwards(Set<ProducerYearsWinDto> producersYearsWin, boolean max) {
        Set<ProducerDto> producersMax = new HashSet<>();
        ProducerDto producer = null;
        for (ProducerYearsWinDto producerYearsWin : producersYearsWin) {
            Set<ProducerDto> producersCurrent = this.searchIntervalYears(producerYearsWin.getYearsWin(), max);
            for (ProducerDto producerCurrent : producersCurrent) {
                if (producer != null) {
                    if (this.isValidInterval(producerCurrent.getInterval(), producer.getInterval(), max)) {
                        producersMax = this.filterMaxMinInterval(producersMax, producerCurrent.getInterval(), max);
                        producer = producerCurrent;
                        producer.setProducer(producerYearsWin.getProducer());
                        producersMax.add(producer);
                    }
                } else {
                    producer = producerCurrent;
                    producer.setProducer(producerYearsWin.getProducer());
                    producersMax.add(producer);
                }
            }
        }
        return producersMax;
    }

    public Set<ProducerDto> searchIntervalYears(Set<Integer> yearsWin, boolean max) {
        Set<ProducerDto> producers = new HashSet<>();
        int interval = 0;
        List<Integer> yearsList = yearsWin.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (int i = 0; i < yearsList.size(); i++) {
            try {
                int next = i+1;
                int intervalCurrent = yearsList.get(i) - yearsList.get(next);
                interval = this.checkInterval(producers, interval, intervalCurrent, yearsList.get(next), yearsList.get(i), max);
            } catch (IndexOutOfBoundsException ex) {
                break;
            }
        }
        producers = this.filterMaxMinInterval(producers, interval, max);
        return producers;
    }

    private int checkInterval(Set<ProducerDto> producers, int interval, int intervalCurrent, int previousWin, int followingWin, boolean max) {
        if (interval == 0 || (max && interval <= intervalCurrent) || (!max && interval >= intervalCurrent)){
            producers.add(new ProducerDto(intervalCurrent, previousWin, followingWin));
            interval = intervalCurrent;
        }
        return interval;
    }

    private Set<ProducerDto> filterMaxMinInterval(Set<ProducerDto> producers, int interval, boolean max) {
        return producers.stream().filter((prod) -> this.isValidInterval(prod.getInterval(), interval, max)).collect(Collectors.toSet());
    }

    public boolean isValidInterval(int intervalCurrent, int interval, boolean max) {
        return (max && intervalCurrent >= interval) || (!max && intervalCurrent <= interval);
    }

}
