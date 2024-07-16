package com.shoppy.order.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppy.cart.model.Cart;
import com.shoppy.cart.model.CartItem;
import com.shoppy.cart.repository.CartDAO;
import com.shoppy.cart.service.CartService;
import com.shoppy.kafka.KafkaProducerService;
import com.shoppy.kafka.PaymentRequestMessageDTO;
import com.shoppy.order.dto.AddressDTO;
import com.shoppy.order.dto.CustomerDTO;
import com.shoppy.order.dto.ItemDTO;
import com.shoppy.order.dto.ItemDTOsResponse;
import com.shoppy.order.dto.OrderDTO;
import com.shoppy.order.dto.OrderItemDTO;
import com.shoppy.order.model.Order;
import com.shoppy.order.model.OrderItem;
import com.shoppy.order.repository.OrderDAO;

@Component
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private CartService cartService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public OrderDTO getOrder(int orderId) {
        Order order = orderDAO.getReferenceById(orderId);

        // convert order to orderdto manually
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setCustomerId(order.getOrderId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());

        // get order Address
        AddressDTO addressDTO = restTemplate.getForObject("http://localhost:8082/api/v1/orderaddresses/" + order.getAddressId(), AddressDTO.class);
        orderDTO.setAddressDTO(addressDTO);

        // get all itemIds so we can get Items of the order so only one call to items
        List<Integer> itemIds = order.getOrderItems().stream().map(OrderItem::getItemId).collect(Collectors.toList());

        // get items from ids
        ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject("http://localhost:8081/api/v1/items/byIds",
                itemIds,
                ItemDTOsResponse.class);

        // create map of items
        Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream()
                .collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

        List<OrderItemDTO> dtos = new ArrayList<OrderItemDTO>();
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

            // get order Address
            AddressDTO addressDTO = restTemplate.getForObject("http://localhost:8082/api/v1/orderaddresses/" + order.getAddressId(), AddressDTO.class);
            orderDTO.setAddressDTO(addressDTO);

            // get all itemIds so we can get Items of the order so only one call to items
            List<Integer> itemIds = order.getOrderItems().stream().map(OrderItem::getItemId)
                    .collect(Collectors.toList());

            // get items from ids
            ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject("http://localhost:8081/api/v1/items/byIds", itemIds, ItemDTOsResponse.class);

            // create map of items
            Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream()
                    .collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

            // for each order, convet order items to order item DTOs and set itemDTO also
            List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(orderItem -> {
                OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
                orderItemDTO.setItemDTO(itemDTOMap.get(orderItemDTO.getItemId()));
                return orderItemDTO;
            }).collect(Collectors.toList());

            orderDTO.setOrderItems(orderItemDTOs);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    public OrderDTO cancelOrder(int orderId) throws Exception{
        // get order
        Order order = orderDAO.getReferenceById(orderId);
        if(order.getStatus().equals("CANCELLED")){
            throw new Exception("Order is already cancelled.");
        }

        // get all itemIds so we can get Items of the order so only one call to items
        List<Integer> itemIds = order.getOrderItems().stream().map(OrderItem::getItemId).collect(Collectors.toList());

        // get items from ids
        ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject("http://localhost:8081/api/v1/items/byIds",
                itemIds,
                ItemDTOsResponse.class);

        // create map of items
        Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream()
                .collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

        ItemDTO itemDto;
        for (OrderItem oi : order.getOrderItems()) {
            // update remaining qunatity of item on cancellation
            int newQuantity = oi.getQuantity() + itemDTOMap.get(oi.getItemId()).getRemainingQuantity();
            itemDTOMap.get(oi.getItemId()).setRemainingQuantity(newQuantity);
            itemDto = restTemplate.postForObject("http://localhost:8081/api/v1/items/", itemDTOMap.get(oi.getItemId()), ItemDTO.class);
        }

        // give refund
        // send a message to kakfa

        // set status of order as cancelled
        orderDAO.cancelOrder(orderId);
        System.out.println("In cancel service method " + order.toString());
        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO createOrder(int userId) throws Exception {

        CustomerDTO customerDTO = restTemplate.getForObject("http://localhost:8082/api/v1/customers/" + userId,
                CustomerDTO.class);

        // if no default address is given
        if (customerDTO.getDefaultAddress() == null) {
            throw new Exception("No default address present for customer");
        }        

        // call cart
        Cart cart = cartDAO.findByUserId(userId);

        // cart doesn't for user or exists but is empty
        if (cart == null || (cart != null && cart.getCartItems().size() == 0)) {
            throw new Exception("Cannot create an order becuase user cart is empty.");
        }

        // if quantity of some cartitem is more than remaining quantity of that item

        // get all itemIds so we can get Items of the order so only one call to items
        List<Integer> itemIds = cart.getCartItems().stream().map(CartItem::getItemId).collect(Collectors.toList());

        // get items from ids
        ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject("http://localhost:8081/api/v1/items/byIds",
                itemIds,
                ItemDTOsResponse.class);

        // create map of items
        Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream()
                .collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

        // now check
        ItemDTO itemDTO;
        for (CartItem ci : cart.getCartItems()) {
            itemDTO = itemDTOMap.get(ci.getItemId());
            if (ci.getQuantity() > itemDTO.getRemainingQuantity()) {
                throw new Exception("Remaining quantity of " + itemDTO.getName() + " is "
                        + itemDTO.getRemainingQuantity() + ". Please decrease item quantity before ordering.");
            }
        }


        if (cart != null) {

            // if cart doesn't have items then show error
            if (cart.getCartItems().size() == 0) {
                throw new Exception("Cannot order; user's cart is empty.");
            }

            // create a order and set its fields from cart
            Order order = new Order();
            order.setCustomerId(userId);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setStatus("PENDING");

            // create orderitems from cartitems
            int price = 0;

            List<OrderItem> orderItems = new ArrayList<OrderItem>();

            for (CartItem ci : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setPrice(ci.getPrice());
                price += ci.getPrice();
                orderItem.setQuantity(ci.getQuantity());
                orderItem.setItemId(ci.getItemId());
                orderItem.setOrder(order);
                orderItems.add(orderItem);

                // reduce remaining quantity of item on order
                int newQuantity = itemDTOMap.get(ci.getItemId()).getRemainingQuantity() - ci.getQuantity();
                itemDTOMap.get(ci.getItemId()).setRemainingQuantity(newQuantity);
                itemDTO = restTemplate.postForObject("http://localhost:8081/api/v1/items/",
                        itemDTOMap.get(ci.getItemId()),
                        ItemDTO.class);
            }
            order.setTotalPrice(price);
            order.setOrderItems(orderItems);
            
            // save address of customer in orderAddress table
            // even if user address is deleted, we will still have order address in this table

            // get customer default address
            AddressDTO orderAddressDTO = restTemplate.getForObject("http://localhost:8082/api/v1/addresses/" + customerDTO.getDefaultAddress(), AddressDTO.class);
            
            // save in orderAddress table
            orderAddressDTO = restTemplate.postForObject("http://localhost:8082/api/v1/orderaddresses/", orderAddressDTO, AddressDTO.class);
            System.out.println("order address saved");     

            // after saving orderAddress add it to order
            order.setAddressId(orderAddressDTO.getAddressId());

            // save order
            OrderDTO orderDTO = modelMapper.map(orderDAO.save(order), OrderDTO.class);
            cartService.deleteCart(userId);

            // send a msg to kafka for payment

            // Create a payment request message
            String paymentRequestMessage = new PaymentRequestMessageDTO(orderDTO.getOrderId(), userId, price, "Shoppy").toString();
            
            // Send message to Kafka
            kafkaProducerService.sendMessage(paymentRequestMessage);
            
            return orderDTO;
        }
        return null;
    }
}
