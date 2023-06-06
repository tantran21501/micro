package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.model.OrderRequest;
import com.example.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save data with Status order Created
        //Product Service -> Block Products (Reduce the quantity)
        //Payment Service -> Payments -> Success -> COMPLETED, ELSE CANCELLED
        log.info("Placing Order Request : {}", orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order  = orderRepository.save(order);
        log.info("Order Place successfully with order id: {}", order.getId());
        return order.getId();
    }
}
