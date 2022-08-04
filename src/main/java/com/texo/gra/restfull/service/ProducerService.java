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
        awardRangeMinMaxDto.setMin(this.searchMinIntervalAwards(producersYearsWin));
        awardRangeMinMaxDto.setMax(this.searchMaxIntervalAwards(producersYearsWin));
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

    private Set<ProducerDto> searchMaxIntervalAwards(Set<ProducerYearsWinDto> producersYearsWin) {
        Set<ProducerDto> producersMax = new HashSet<>();
        ProducerDto producer = null;
        for (ProducerYearsWinDto producerYearsWin : producersYearsWin) {
            ProducerDto producerCurrent = this.searchIntervalYears(producerYearsWin.getYearsWin(), true);
            if (producer != null) {
                if (producer.getInterval() <= producerCurrent.getInterval()) {
                    producersMax = producersMax.stream().filter((prod) -> prod.getInterval() >= producerCurrent.getInterval()).collect(Collectors.toSet());
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
        return producersMax;
    }

    private Set<ProducerDto> searchMinIntervalAwards(Set<ProducerYearsWinDto> producersYearsWin) {
        Set<ProducerDto> producersMin = new HashSet<>();
        ProducerDto producer = null;
        for (ProducerYearsWinDto producerYearsWin : producersYearsWin) {
            ProducerDto producerCurrent = this.searchIntervalYears(producerYearsWin.getYearsWin(), false);
            if (producer != null) {
                if (producer.getInterval() >= producerCurrent.getInterval()) {
                    producersMin = producersMin.stream().filter((prod) -> prod.getInterval() <= producerCurrent.getInterval()).collect(Collectors.toSet());
                    producer = producerCurrent;
                    producer.setProducer(producerYearsWin.getProducer());
                    producersMin.add(producer);
                }
            } else {
                producer = producerCurrent;
                producer.setProducer(producerYearsWin.getProducer());
                producersMin.add(producer);
            }
        }
        return producersMin;
    }

    public ProducerDto searchIntervalYears(Set<Integer> yearsWin, boolean max) {
        ProducerDto producerDto = null;
        List<Integer> yearsList = yearsWin.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (int i = 0; i < yearsList.size(); i++) {
            try {
                int next = i+1;
                int intervalCurrent = yearsList.get(i) - yearsList.get(next);
                producerDto = this.checkInterval(producerDto, intervalCurrent, yearsList.get(next), yearsList.get(i), max);
            } catch (IndexOutOfBoundsException ex) {
                break;
            }
        }
        return producerDto;
    }

    private ProducerDto checkInterval(ProducerDto producerDto, int intervalCurrent, int previousWin, int followingWin, boolean max) {
        if (producerDto == null || (max && producerDto.getInterval() <= intervalCurrent) || (!max && producerDto.getInterval() >= intervalCurrent)){
            return new ProducerDto(intervalCurrent, previousWin, followingWin);
        }
        return producerDto;
    }

}
