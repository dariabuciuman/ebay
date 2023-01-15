package com.ebay.server.kafka;

import com.ebay.server.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public final class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;
    private final String TOPIC = "expiredProducts";

    public ProducerService(KafkaTemplate<String, ProductDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, ProductDTO productDTO) {
        kafkaTemplate.send(topic, productDTO);
        logger.info(String.format("$$$$ => Producing message: %s", productDTO.getName()));
    }
}
