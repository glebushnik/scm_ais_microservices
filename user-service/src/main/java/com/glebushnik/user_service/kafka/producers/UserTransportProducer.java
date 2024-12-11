package com.glebushnik.user_service.kafka.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserTransportProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void sendAssignment(String topic, Map<String, Object> data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(topic, json);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error serializing data to JSON", e);
        }
    }
}
