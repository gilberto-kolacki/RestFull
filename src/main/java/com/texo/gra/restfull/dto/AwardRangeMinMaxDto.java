package com.texo.gra.restfull.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AwardRangeMinMaxDto {

    private Set<ProducerDto> min;
    private Set<ProducerDto> max;

    public AwardRangeMinMaxDto() {
        this.min = new HashSet<>();
        this.max = new HashSet<>();
    }

}
