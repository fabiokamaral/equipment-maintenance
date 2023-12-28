package com.challenge.equipmentmaintenance.repository;

import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.util.OrderCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@Testcontainers
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl("embedded"));
    }

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Save persists order when successful")
    void save_PersistsOrder_WhenSuccessful() {

        Order order = orderRepository.save(OrderCreator.createOrderToSave());

        Assertions.assertThat(order.getId()).isNotNull();
        Assertions.assertThat(order.getCreatedDate()).isNotNull();
        Assertions.assertThat(order.getLastModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Find Order By Id returns order when successful")
    void findOrderById_ReturnsOrder_WhenSuccessful() {

        Order order = orderRepository.save(OrderCreator.createOrderWithOrderIdToSave());

        Optional<Order> orderFound = orderRepository.findByOrderId(order.getOrderId());

        Assertions.assertThat(orderFound).isPresent();
    }

    @Test
    @DisplayName("Find By Responsible Registration returns list of orders when successful")
    void findByResponsibleRegistration_ReturnsOrders_WhenSuccessful() {

        Order order = orderRepository.save(OrderCreator.createOrderWithOrderIdToSave());
        String expectedRegistration = order.getResponsible().getRegistration();

        List<Order> ordersFound = orderRepository.findByResponsibleRegistration(order.getResponsible().getRegistration());

        Assertions.assertThat(ordersFound).isNotEmpty().hasSize(1);
        Assertions.assertThat( ordersFound.get(0).getResponsible().getRegistration()).isEqualTo(expectedRegistration);
    }

}