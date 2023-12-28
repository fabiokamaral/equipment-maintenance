package com.challenge.equipmentmaintenance.service;

import com.challenge.equipmentmaintenance.exceptions.BadRequestException;
import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.repository.OrderRepository;
import com.challenge.equipmentmaintenance.util.OrderCreator;
import com.challenge.equipmentmaintenance.util.OrderPostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DatabaseSequenceGeneratorService databaseSequenceGeneratorService;

    @Test
    @DisplayName("listAll returns list of order inside page object when successful")
    void listAll_ReturnsListOfOrderInsidePageObject_WhenSuccessful() {

        Order orderSaved = OrderCreator.createSavedOrder();
        PageImpl<Order> orderList = new PageImpl<>(List.of(orderSaved));
        BDDMockito.when(orderRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(orderList);

        Page<Order> orderPage = orderService.listAll(PageRequest.of(1, 20));

        Assertions.assertThat(orderPage).isNotNull();
        Assertions.assertThat(orderPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(orderPage.toList().get(0).getCustomer().getName())
                .isEqualTo(orderSaved.getCustomer().getName());
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns order when successful")
    void findByIdOrThrowBadRequestException_ReturnsOrder_WhenSuccessful() {

        Order orderSaved = OrderCreator.createSavedOrder();
        BDDMockito.when(orderRepository.findByOrderId(ArgumentMatchers.any())).thenReturn(Optional.of(orderSaved));

        Long expectedId = orderSaved.getOrderId();
        Order order = orderService.findByIdOrThrowBadRequestException(1000L);

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getOrderId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when order is not found")
    void findByIdOrThrowBadRequestException_throws_BadRequestException_when_order_is_not_found() {
        BDDMockito.when(orderRepository.findByOrderId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> orderService.findByIdOrThrowBadRequestException(1L));
    }

    @Test
    @DisplayName("findByName returns order when successful")
    void findByName_ReturnsOrder_WhenSuccessful() {

        Order order = OrderCreator.createSavedOrder();
        BDDMockito.when(orderRepository.findByResponsibleRegistration(ArgumentMatchers.any())).thenReturn(List.of(order));

        String expectedRegistration = order.getResponsible().getRegistration();
        List<Order> orderList = orderService.findByResponsibleRegistration("Responsible Name");

        Assertions.assertThat(orderList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(orderList.get(0).getResponsible().getRegistration()).isEqualTo(expectedRegistration);
    }

    @Test
    @DisplayName("findByName returns a empty list of order when order is not found")
    void findByName_ReturnsEmptyListOfOrder_WhenOrderIsNotFound() {

        BDDMockito.given(orderRepository.findByResponsibleRegistration(ArgumentMatchers.anyString()))
                .willReturn(Collections.emptyList());
        List<Order> orderList = orderService.findByResponsibleRegistration("name");

        Assertions.assertThat(orderList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns order when successful")
    void save_ReturnsOrder_WhenSuccessful() {

        BDDMockito.when(databaseSequenceGeneratorService.generateSequence(ArgumentMatchers.anyString())).thenReturn(1L);
        BDDMockito.when(orderRepository.save(ArgumentMatchers.any())).thenReturn(OrderCreator.createSavedOrder());

        Order orderSaved = orderService.save(OrderPostRequestBodyCreator.create());

        Assertions.assertThat(orderSaved)
                .isNotNull()
                .isEqualTo(OrderCreator.createSavedOrder());
    }

    @Test
    @DisplayName("delete removes order when successful")
    void delete_RemovesOrder_WhenSuccessful() {

        BDDMockito.when(orderRepository.findByOrderId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(OrderCreator.createSavedOrder()));

        Assertions.assertThatCode(() -> orderService.delete(1L))
                .doesNotThrowAnyException();
    }

}