package com.shoppy.order.kafka;

import java.sql.Time;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.shoppy.order.dto.OrderDTO;
import com.shoppy.order.repository.OrderDAO;
import com.shoppy.order.service.OrderService;

@Service
public class KafkaConsumerService {
    
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LogManager.getLogger(KafkaConsumerService.class);

    // process order request from frontend clients
    @KafkaListener(topics = "order-request", groupId = "order-group")
    public void listenForOrders(String message) {
        logger.info("Received message from 'order-request' topic: {}", message);
        System.out.println(message);
        try {
            int userId = Integer.parseInt(message);
            OrderDTO orderDTO = orderService.createOrder(userId);
            System.out.println(orderDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("some issue");
        }
    }

    @KafkaListener(topics = "payment-response", groupId = "payment-group")
    public void listenForPaymentResponses(String message) {
        logger.info("Received message from 'payment-response' topic: {}", message);

        // Parse received message into PaymentResponseMessageDTO
        PaymentResponseMessageDTO paymentResponseMessageDto = parsePaymentResponse(message);
        
        if (paymentResponseMessageDto != null) {
            String status = null;

            // Determine the status based on the payment response
            if ("CLEARED".equals(paymentResponseMessageDto.getStatus())) {
                status = "SUCCESSFUL";
                logger.info("Payment cleared for order ID: {}. Updating status to: {}", paymentResponseMessageDto.getOrderId(), status);
            } else if ("DECLINED".equals(paymentResponseMessageDto.getStatus())) {
                status = "PAYMENT DECLINED";
                logger.info("Payment declined for order ID: {}. Updating status to: {}", paymentResponseMessageDto.getOrderId(), status);
            }

            // Update the status of the order based on the received message
            if (status != null) {
                orderDAO.updateOrderStatus(paymentResponseMessageDto.getOrderId(), status);
                logger.info("Order ID: {} status updated to: {}", paymentResponseMessageDto.getOrderId(), status);
            } else {
                logger.warn("Received unknown payment status for order ID: {}. No status update performed.", paymentResponseMessageDto.getOrderId());
            }
        } else {
            logger.warn("Failed to parse payment response message: {}", message);
        }
    }

    // Static method to parse the string representation into PaymnetResponseMessage object
    public static PaymentResponseMessageDTO parsePaymentResponse(String str) {
        str = str.substring(1, str.length() - 1); // Remove enclosing curly braces {}
        String[] parts = str.split(", "); // Split by comma followed by space

        PaymentResponseMessageDTO message = new PaymentResponseMessageDTO();

        for (String part : parts) {
            String[] keyValue = part.split("='", 2); // Split by equals sign, limit to 2 parts
            String key = keyValue[0].trim();
            String value = keyValue[1].substring(0, keyValue[1].length() - 1); // Remove trailing single quote

            switch (key) {
                case "orderId":
                    message.setOrderId(Integer.parseInt(value));
                    break;
                case "userId":
                    message.setUserId(Integer.parseInt(value));
                    break;
                case "txnAmount":
                    message.setTxnAmount(Integer.parseInt(value));
                    break;
                case "service":
                    message.setService(value);
                    break;
                case "time":
                    message.setTime(Time.valueOf(value));
                    break;
                case "paymentId":
                    message.setPaymentId(Integer.parseInt(value));
                    break;
                case "status":
                    message.setStatus(value);
                    break;
                case "description":
                    message.setDescription(value);
                    break;
                default:
                    // Handle unknown keys if necessary
                    break;
            }
        }
        return message;
    }
}
