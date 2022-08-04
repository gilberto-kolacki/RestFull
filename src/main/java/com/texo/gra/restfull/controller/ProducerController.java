package com.texo.gra.restfull.controller;

import com.texo.gra.restfull.dto.AwardRangeMinMaxDto;
import com.texo.gra.restfull.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProducerController {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public ResponseEntity<?> getReward() throws Exception {
        AwardRangeMinMaxDto awardRangeMinMaxDto = this.producerService.getAwardRangeMinMax();
        return ResponseEntity.ok().body(awardRangeMinMaxDto);
    }
}
