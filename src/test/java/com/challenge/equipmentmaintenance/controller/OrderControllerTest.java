package com.challenge.equipmentmaintenance.controller;

import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.service.OrderService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    @DisplayName("list returns list of orders inside PageObject when successful")
    void list_ReturnsListOfOrdersInsidePageObject_WhenSuccessful() {

        PageImpl<Order> orderPage = new PageImpl<>(List.of(OrderCreator.createSavedOrder()));
        BDDMockito.when(orderService.listAll(ArgumentMatchers.any())).thenReturn(orderPage);

        String expectedOrderCustomerName = OrderCreator.createSavedOrder().getCustomer().getName();
        Page<Order> pageOrder = orderController.listAll(null).getBody();

        Assertions.assertThat(pageOrder).isNotNull();
        Assertions.assertThat(pageOrder.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(pageOrder.toList().get(0).getCustomer().getName()).isEqualTo(expectedOrderCustomerName);
    }

    @Test
    @DisplayName("findByOrderId returns order when successful")
    void findByOrderId_ReturnsOrder_WhenSuccessful() {

        Order orderToSave = OrderCreator.createOrderWithOrderIdToSave();
        BDDMockito.when(orderService.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(orderToSave);

        Long expectedId = orderToSave.getOrderId();
        Order order = orderController.findByOrderId(1000L).getBody();

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getOrderId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByResponsibleRegistration returns order list when successful")
    void findByResponsibleRegistration_ReturnsOrderList_WhenSuccessful() {
        Order orderSaved = OrderCreator.createSavedOrder();
        List<Order> orderListSaved = List.of(orderSaved);
        BDDMockito.when(orderService.findByResponsibleRegistration(ArgumentMatchers.any())).thenReturn(orderListSaved);

        String expectedName = orderSaved.getCustomer().getName();
        List<Order> orderList = orderController.findByRegistration("MT1234").getBody();

        Assertions.assertThat(orderList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(orderList.get(0).getCustomer().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByResponsibleRegistration returns a empty list of order when order is not found")
    void findByResponsibleRegistration_ReturnsEmptyListOfOrder_WhenOrderIsNotFound() {

        BDDMockito.given(orderService.findByResponsibleRegistration(ArgumentMatchers.anyString()))
                .willReturn(Collections.emptyList());
        List<Order> orderList = orderController.findByRegistration("name").getBody();

        Assertions.assertThat(orderList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns order when successful")
    void save_ReturnsOrder_whenSuccessful() {

        BDDMockito.when(orderService.save(ArgumentMatchers.any())).thenReturn(OrderCreator.createSavedOrder());

        Order orderSaved = orderController.save(OrderPostRequestBodyCreator.create()).getBody();

        Assertions.assertThat(orderSaved).isNotNull().isEqualTo(OrderCreator.createSavedOrder());
    }

    @Test
    @DisplayName("delete removes order when successful")
    void delete_RemovesOrder_WhenSuccessful() {

        BDDMockito.doNothing().when(orderService).delete(ArgumentMatchers.anyLong());

        Assertions.assertThatCode(() -> orderController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = orderController.delete(1L);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}