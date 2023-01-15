package com.example.expiredproductsserver.service;

import com.example.expiredproductsserver.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "expiredProducts")
    public void processMessage(ConsumerRecord<String, ProductDTO> record) {
        log.info("Received Message: " + record.value().getName());
        System.out.println("Received Message: " + record.value().getName());
    }
}
