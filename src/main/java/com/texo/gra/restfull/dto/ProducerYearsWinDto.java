package com.texo.gra.restfull.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class ProducerYearsWinDto {

    private final String producer;
    private final Set<Integer> yearsWin;

    public ProducerYearsWinDto(String producer, Set<Integer> yearsWin) {
        this.producer = producer;
        this.yearsWin = yearsWin;
    }
}
