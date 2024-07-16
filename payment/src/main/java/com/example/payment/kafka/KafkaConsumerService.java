package com.example.payment.kafka;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.payment.dao.PaymentDAO;
import com.example.payment.dto.PaymentRequestDTO;
import com.example.payment.model.Payment;

@Service
public class KafkaConsumerService {
    
    
    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final Logger logger = LogManager.getLogger(KafkaConsumerService.class);


    @KafkaListener(topics = "payment-request", groupId = "payment-group")
    public void listen(String message) {
        logger.info("Received payment request message: {}", message);

        // Parse the JSON message
        PaymentRequestDTO paymentRequestDto = parsePaymentRequest(message);
        if (paymentRequestDto != null) {
            logger.info("Parsed payment request: {}", paymentRequestDto);

            // Set the current time and status for the payment request
            paymentRequestDto.setTime(new Time(System.currentTimeMillis()));
            paymentRequestDto.setStatus("CLEARED");
            paymentRequestDto.setDescription("Payment was successful.");
            logger.info("Updated payment request with time and status: {}", paymentRequestDto);

            // Save payment to the database
            Payment payment = paymentDAO.save(modelMapper.map(paymentRequestDto, Payment.class));
            logger.info("Saved payment to database: {}", payment);

            // Map saved payment back to PaymentRequestDTO
            paymentRequestDto = modelMapper.map(payment, PaymentRequestDTO.class);
            logger.info("Mapped saved payment back to PaymentRequestDTO: {}", paymentRequestDto);

            // Simulate a 7-second delay to mimic payment verification
            try {
                logger.info("Simulating a 7-second delay for payment verification.");
                Thread.sleep(7000); // 7000 milliseconds = 7 seconds
            } catch (InterruptedException e) {
                logger.error("Thread interrupted during delay: {}", e.getMessage());
                Thread.currentThread().interrupt();
                // Handle interruption if needed
            }

            // Send Kafka message after delay
            kafkaProducerService.sendMessage(paymentRequestDto.toString());
            logger.info("Sent payment response message to Kafka: {}", paymentRequestDto);
        } else {
            logger.warn("Failed to parse payment request message: {}", message);
        }
    }

    private PaymentRequestDTO parsePaymentRequest(String message) {
        
        try {
            // Remove the class name from the string
            message = message.replace("PaymentRequestMessageDTO(", "").replace(")", "");
            System.out.println(message);
            // Split the remaining string by commas and equals sign
            String[] parts = message.split(", ");

            Map<String, String> keyValueMap = new HashMap<>();

            // Extract key-value pairs
            for (String part : parts) {
                String[] keyValue = part.split("=");
                if (keyValue.length == 2) {
                    keyValueMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }

            // Create PaymentMessageDTO object
            PaymentRequestDTO paymentMessageDTO = new PaymentRequestDTO();
            paymentMessageDTO.setOrderId(Integer.valueOf(keyValueMap.get("orderId")));
            paymentMessageDTO.setUserId(Integer.valueOf(keyValueMap.get("userId")));
            paymentMessageDTO.setTxnAmount(Integer.parseInt(keyValueMap.get("txnAmount")));
            paymentMessageDTO.setService(keyValueMap.get("service"));

            return paymentMessageDTO;
        } catch (Exception e) {
            System.out.println("Exception occured");
            return null;
        }
    }
}
