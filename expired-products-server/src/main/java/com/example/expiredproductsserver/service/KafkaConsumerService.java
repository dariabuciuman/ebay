package com.example.expiredproductsserver.service;

import com.example.expiredproductsserver.dto.ExpiredProductDTO;
import com.example.expiredproductsserver.repo.ProductRepository;
import com.example.expiredproductsserver.util.ProductUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    final ProductService productService;

    public KafkaConsumerService(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "expiredProducts")
    public void processMessage(ConsumerRecord<String, ExpiredProductDTO> record) {
        log.info("Received Message: " + record.value().getName());
        System.out.println("Received Message: " + record.value().getName());
        productService.addProduct(record.value());
    }
}
