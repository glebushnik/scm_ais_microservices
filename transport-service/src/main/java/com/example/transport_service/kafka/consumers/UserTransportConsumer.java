package com.example.transport_service.kafka.consumers;

import com.example.transport_service.service.TransportService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserTransportConsumer {

    private final ObjectMapper objectMapper;
    private final TransportService transportService;

    @KafkaListener(topics = "user-transport-assignment-topic", groupId = "transport-service-group")
    public void consume(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            UUID userId = UUID.fromString(jsonNode.get("userId").asText());
            List<UUID> transportIds = StreamSupport.stream(
                            jsonNode.get("transportIds").spliterator(), false)
                    .map(JsonNode::asText)
                    .map(UUID::fromString)
                    .collect(Collectors.toList());

            transportService.assignUserToTransport(transportIds, userId);
        } catch (Exception e) {
            throw new RuntimeException("Error processing Kafka message", e);
        }
    }
}
