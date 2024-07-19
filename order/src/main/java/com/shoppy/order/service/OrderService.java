package com.shoppy.order.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppy.order.dto.AddressDTO;
import com.shoppy.order.dto.CartDTO;
import com.shoppy.order.dto.CartItemDTO;
import com.shoppy.order.dto.CustomerDTO;
import com.shoppy.order.dto.ItemDTO;
import com.shoppy.order.dto.ItemDTOsResponse;
import com.shoppy.order.dto.OrderDTO;
import com.shoppy.order.dto.OrderItemDTO;
import com.shoppy.order.kafka.KafkaProducerService;
import com.shoppy.order.kafka.PaymentRequestMessageDTO;
import com.shoppy.order.model.Order;
import com.shoppy.order.model.OrderItem;
import com.shoppy.order.repository.OrderDAO;


@Component
public class OrderService {

    private static final String CUSTOMER_SERVICE_BASE_URL = "http://customer-service/api/v1/";
    private static final String ITEM_SERVICE_BASE_URL = "http://item-service/api/v1/";
    private static final String CART_SERVICE_BASE_URL = "http://cart-service/api/v1/carts/";

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public OrderDTO getOrder(int orderId) {
        Order order = orderDAO.getReferenceById(orderId);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setCustomerId(order.getOrderId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());

        AddressDTO addressDTO = restTemplate.getForObject(CUSTOMER_SERVICE_BASE_URL + "orderaddresses/" + order.getAddressId(), AddressDTO.class);
        orderDTO.setAddressDTO(addressDTO);

        List<Integer> itemIds = order.getOrderItems().stream().map(OrderItem::getItemId).collect(Collectors.toList());

        ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject(ITEM_SERVICE_BASE_URL + "items/byIds", itemIds, ItemDTOsResponse.class);

        Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream().collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

        List<OrderItemDTO> dtos = new ArrayList<>();
        for (OrderItem oi : order.getOrderItems()) {
            OrderItemDTO orderItemDTO = modelMapper.map(oi, OrderItemDTO.class);
            orderItemDTO.setItemDTO(itemDTOMap.get(oi.getItemId()));
            dtos.add(orderItemDTO);
        }
        orderDTO.setOrderItems(dtos);

        return orderDTO;
    }

    public List<OrderDTO> getOrdersByCustomerId(int customerId) {
        List<Order> orders = orderDAO.findAllByCustomerId(customerId);
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setCustomerId(order.getCustomerId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setTotalPrice(order.getTotalPrice());

            AddressDTO addressDTO = restTemplate.getForObject(CUSTOMER_SERVICE_BASE_URL + "orderaddresses/" + order.getAddressId(), AddressDTO.class);
            orderDTO.setAddressDTO(addressDTO);

            List<Integer> itemIds = order.getOrderItems().stream().map(OrderItem::getItemId).collect(Collectors.toList());

            ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject(ITEM_SERVICE_BASE_URL + "items/byIds", itemIds, ItemDTOsResponse.class);

            Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream().collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

            List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(orderItem -> {
                OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
                orderItemDTO.setItemDTO(itemDTOMap.get(orderItem.getItemId()));
                return orderItemDTO;
            }).collect(Collectors.toList());

            orderDTO.setOrderItems(orderItemDTOs);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    public OrderDTO cancelOrder(int orderId) throws Exception {
        Order order = orderDAO.getReferenceById(orderId);
        if (order.getStatus().equals("CANCELLED")) {
            throw new Exception("Order is already cancelled.");
        }

        List<Integer> itemIds = order.getOrderItems().stream().map(OrderItem::getItemId).collect(Collectors.toList());

        ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject(ITEM_SERVICE_BASE_URL + "items/byIds", itemIds, ItemDTOsResponse.class);

        Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream().collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

        for (OrderItem oi : order.getOrderItems()) {
            int newQuantity = oi.getQuantity() + itemDTOMap.get(oi.getItemId()).getRemainingQuantity();
            itemDTOMap.get(oi.getItemId()).setRemainingQuantity(newQuantity);
            restTemplate.postForObject(ITEM_SERVICE_BASE_URL + "items/", itemDTOMap.get(oi.getItemId()), ItemDTO.class);
        }

        orderDAO.cancelOrder(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO createOrder(int userId) throws Exception {
        CustomerDTO customerDTO = restTemplate.getForObject(CUSTOMER_SERVICE_BASE_URL + "customers/" + userId, CustomerDTO.class);

        if (customerDTO.getDefaultAddress() == null) {
            throw new Exception("No default address present for customer");
        }

        CartDTO cart = restTemplate.getForObject(CART_SERVICE_BASE_URL + userId, CartDTO.class);

        if (cart == null || (cart != null && cart.getCartDtoItems().isEmpty())) {
            throw new Exception("Cannot create an order because user cart is empty.");
        }

        List<Integer> itemIds = cart.getCartDtoItems().stream().map(CartItemDTO::getItemId).collect(Collectors.toList());

        ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject(ITEM_SERVICE_BASE_URL + "items/byIds", itemIds, ItemDTOsResponse.class);

        Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream().collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

        for (CartItemDTO ci : cart.getCartDtoItems()) {
            ItemDTO itemDTO = itemDTOMap.get(ci.getItemId());
            if (ci.getQuantity() > itemDTO.getRemainingQuantity()) {
                throw new Exception("Remaining quantity of " + itemDTO.getName() + " is " + itemDTO.getRemainingQuantity() + ". Please decrease item quantity before ordering.");
            }
        }

        Order order = new Order();
        order.setCustomerId(userId);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setStatus("PENDING");

        int price = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDTO ci : cart.getCartDtoItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(ci.getPrice());
            price += ci.getPrice() * ci.getQuantity();
            orderItem.setQuantity(ci.getQuantity());
            orderItem.setItemId(ci.getItemId());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            int newQuantity = itemDTOMap.get(ci.getItemId()).getRemainingQuantity() - ci.getQuantity();
            itemDTOMap.get(ci.getItemId()).setRemainingQuantity(newQuantity);
            restTemplate.postForObject(ITEM_SERVICE_BASE_URL + "items/", itemDTOMap.get(ci.getItemId()), ItemDTO.class);
        }
        order.setTotalPrice(price);
        order.setOrderItems(orderItems);

        AddressDTO orderAddressDTO = restTemplate.getForObject(CUSTOMER_SERVICE_BASE_URL + "addresses/" + customerDTO.getDefaultAddress(), AddressDTO.class);
        orderAddressDTO = restTemplate.postForObject(CUSTOMER_SERVICE_BASE_URL + "orderaddresses/", orderAddressDTO, AddressDTO.class);
        order.setAddressId(orderAddressDTO.getAddressId());

        OrderDTO orderDTO = modelMapper.map(orderDAO.save(order), OrderDTO.class);
        restTemplate.exchange(CART_SERVICE_BASE_URL + userId, HttpMethod.DELETE, null, String.class);

        String paymentRequestMessage = new PaymentRequestMessageDTO(orderDTO.getOrderId(), userId, price, "Shoppy").toString();
        kafkaProducerService.sendMessage(paymentRequestMessage);

        return orderDTO;
    }
}