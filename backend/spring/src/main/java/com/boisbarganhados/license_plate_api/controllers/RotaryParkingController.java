package com.boisbarganhados.license_plate_api.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boisbarganhados.license_plate_api.dtos.requests.CheckParkingRequest;
import com.boisbarganhados.license_plate_api.dtos.responses.PlateValidationResource;
import com.boisbarganhados.license_plate_api.exceptions.InvalidRequestException;
import com.boisbarganhados.license_plate_api.exceptions.PlateInvalidFormatException;
import com.boisbarganhados.license_plate_api.exceptions.PlateNotFoundException;
import com.boisbarganhados.license_plate_api.exceptions.PlateOCRFailedException;
import com.boisbarganhados.license_plate_api.exceptions.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Service
@Slf4j
@RequestMapping("/api/rotary-parking")
@RequiredArgsConstructor
public class RotaryParkingController extends BaseController {

    private final KafkaProducer<String, String> producer;
    private final KafkaTemplate<String, String> kafkaTemplate;   
    private final KafkaConsumer<String, String> consumer;
    private static final String requestTopicName = "license-plate-topic-1";
    private static final String responseTopicName = "py-response-topic-1";
    private static final int MAX_TIMEOUT = 120000; // 2 minutes

    /**
     * Send message to Kafka topic
     * 
     * @param msg message to send
     */
    public void sendMessage(String msg) {
        producer.send(new ProducerRecord<>(requestTopicName, msg));
        producer.flush();
        log.info("Payload enviado: {}" + msg);
    }

    @PostMapping
    public ResponseEntity<PlateValidationResource> rotaryParking(@ModelAttribute @Valid CheckParkingRequest request)
            throws ServiceException, InvalidRequestException, Exception, PlateInvalidFormatException,
            PlateNotFoundException, PlateOCRFailedException {
        // Get file from request
        log.info(API_PATH + "POST /api/rotary-parking");
        String mountFolderPath = "/app";
        var file = request.file();
        var uuid = UUID.randomUUID().toString();
        // Construct the destination path
        Path destinationPath = Paths.get(mountFolderPath, uuid + file.getOriginalFilename());
        Files.write(destinationPath, file.getBytes());
        // start KAFKA producer to send message to plate validation service
        sendMessage(destinationPath.toString());

        kafkaTemplate.send(requestTopicName, destinationPath.toString());
        kafkaTemplate.flush();
            
        //wait response from plate validation service via topic py-response-topic-1
        consumer.subscribe(Collections.singletonList(responseTopicName));
        var response = "";
        long startTime = System.currentTimeMillis();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); 
            for (ConsumerRecord<String, String> record : records) {
                String key = record.key();
                String value = record.value();
                log.info("Received message: key = %s, value = %s%n", key, value);
                response = value;
                if(response == null || response.isEmpty()) {
                    response = "";
                }
            }
            if (!response.isEmpty()) {
                break;
            }
            if (System.currentTimeMillis() - startTime > MAX_TIMEOUT) {
                throw new ServiceException("Timeout exceeded");
            }
        }
        return ResponseEntity.ok(new PlateValidationResource("Validado!", response));
    }
}