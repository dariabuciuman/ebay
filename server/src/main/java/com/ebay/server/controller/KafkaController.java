package com.ebay.server.controller;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/kafka")
public final class KafkaController {
    private final String TOPIC = "expiredProducts";
    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @RequestMapping(value = "/send_product", method = RequestMethod.POST)
    public void sendMessage(@RequestBody ProductDTO productDTO) {
        producerService.sendMessage(TOPIC, productDTO);
    }
}