package com.texo.gra.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDto {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

    public ProducerDto(Integer interval, Integer previousWin, Integer followingWin) {
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }
}
