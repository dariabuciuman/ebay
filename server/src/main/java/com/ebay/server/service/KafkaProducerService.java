package com.ebay.server.service;

import com.ebay.server.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public final class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, ProductDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, ProductDTO productDTO) {
        kafkaTemplate.send(topic, productDTO);
        logger.info(String.format("$$$$ => Sending product: %s", productDTO.getName()));
    }
}
