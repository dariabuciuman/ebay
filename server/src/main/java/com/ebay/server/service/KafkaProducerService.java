package com.ebay.server.service;

import com.ebay.server.dto.ExpiredProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public final class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, ExpiredProductDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, ExpiredProductDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, ExpiredProductDTO expiredProductDTO) {
        kafkaTemplate.send(topic, expiredProductDTO);
        logger.info(String.format("$$$$ => Sending product: %s", expiredProductDTO.getName()));
    }
}
