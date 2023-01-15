package com.ebay.server.controller;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.kafka.ConsumerService;
import com.ebay.server.kafka.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/kafka")
public final class KafkaController {
    private final String TOPIC = "expiredProducts";
    /*private final ProducerService producerService;
    private final ConsumerService consumerService;*/

    /*private KafkaTemplate<String, ProductDTO> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, ProductDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @RequestMapping(value = "/send_product", method = RequestMethod.POST)
    public ResponseEntity<Object> sendProduct(@RequestBody ProductDTO productDTO) {
        log.info("sending product to kafka");
        kafkaTemplate.send(TOPIC, productDTO);
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }*/
    private final ProducerService producerService;

    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @RequestMapping(value = "/send_product", method = RequestMethod.POST)
    public void sendMessage(@RequestBody ProductDTO productDTO) {
        producerService.sendMessage(TOPIC, productDTO);
    }


    /*public KafkaController(ProducerService producerService, ConsumerService consumerService) {
        this.producerService = producerService;
        this.consumerService = consumerService;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam String message) {
        producerService.sendMessage(message);
    }

    @GetMapping(value = "/consume")
    public void consumeMessageFromKafkaTopic(@RequestParam String message) {
        consumerService.consume(message);
    }
     */
}